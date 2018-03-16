import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InputFileReader {

    private String inputFilePath;
    private Connection connection;

    public InputFileReader(String inputFilePath, Connection connection) {
        this.inputFilePath = inputFilePath;
        this.connection = connection;
    }

    public List<Response> generateResponses() {
        List<Response> outputList = new ArrayList<>();
        ItemDao itemDao = new ItemDaoImpl(connection);
        OrderDao orderDao = new OrderDaoImpl(connection);

        Integer previousOrderId = null;
        try {
            // instantiate reader
            Reader reader = Files.newBufferedReader(Paths.get(inputFilePath));
            CSVFormat ownFormat = CSVFormat.newFormat(';');
            CSVParser csvParser = new CSVParser(reader, ownFormat
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim());

            // line by line
            for (CSVRecord records : csvParser.getRecords()) {
                Item item = new Item();
                Order order = new Order();
                Response response = new Response();

                // line number set
                String lineNumber = records.get("LineNumber");
                if (!lineNumber.isEmpty()) {
                    response.setLineNumber(Integer.valueOf(lineNumber));
                }


                // checking the number of fields
                if (records.size() == 12) {

                    // checking if there are empty fields
                    if (Util.noEmptyFields(records.toMap())) {

                        // orderItemId checks
                        Integer itemId;
                        try {
                            itemId = Integer.valueOf(records.get("OrderItemId"));
                        } catch (NumberFormatException e) {
                            response.setStatus(Util.ResponseFileStatus.ERROR);
                            response.setMessage("OrderItemId '" + records.get("OrderId") + "' is not an int value.");
                            outputList.add(response);
                            continue;
                        }
                        if (itemDao.getItem(itemId) == null) {
                            item.setId(itemId);
                        } else {
                            response.setStatus(Util.ResponseFileStatus.ERROR);
                            response.setMessage("There is already an Item with id '" + itemId + "'.");
                            outputList.add(response);
                            continue;
                        }

                        // orderId checks
                        Integer orderId;
                        try {
                            orderId = Integer.valueOf(records.get("OrderId"));
                        } catch (NumberFormatException e) {
                            response.setStatus(Util.ResponseFileStatus.ERROR);
                            response.setMessage("OrderId '" + records.get("OrderId") + "' is not an int value.");
                            outputList.add(response);
                            continue;
                        }
                        // checks that the order is a new one or if it was the previous one
                        // if not then i have to check everything if its valid
                        if (previousOrderId == null || !orderId.equals(previousOrderId)) {
                            if (orderDao.getOrder(orderId) != null) {
                                response.setStatus(Util.ResponseFileStatus.ERROR);
                                response.setMessage("There is already an Order with id '" + orderId + "'.");
                                outputList.add(response);
                                continue;
                            }
                            order.setId(orderId);

                            order.setBuyerName(records.get("BuyerName"));

                            // email validation
                            String email = records.get("BuyerEmail");
                            if (Util.validEmail(email)) {
                                order.setBuyerEmail(email);
                            } else {
                                response.setStatus(Util.ResponseFileStatus.ERROR);
                                response.setMessage("BuyerEmail '" + email + "' is not a valid email.");
                                outputList.add(response);
                                continue;
                            }

                            // order date validation
                            String date = records.get("OrderDate");
                            if (Util.emptyOrderDate(date)) {
                                order.setOrderDate(new Date());
                            } else if (Util.validOrderDate(date, "yyyy-MM-dd")) {
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                order.setOrderDate(format.parse(date));
                            } else {
                                response.setStatus(Util.ResponseFileStatus.ERROR);
                                response.setMessage("OrderDate '" + date + "' is not a valid date.");
                                outputList.add(response);
                                continue;
                            }

                            order.setAddress(records.get("Address"));

                            // postcode validation
                            String postcode = records.get("Postcode");
                            if (Util.validPostcode(postcode)) {
                                order.setPostcode(Integer.valueOf(postcode));
                            } else {
                                response.setStatus(Util.ResponseFileStatus.ERROR);
                                response.setMessage("Postcode '" + postcode + "' is not a valid Postcode.");
                                outputList.add(response);
                                continue;
                            }
                            // if the order was the previous one, we simply just load it from the db(all fields are valid)
                        } else {
                            order = orderDao.getOrder(orderId);
                        }
                        item.setOrderId(orderId);

                        // sale price validation
                        String salePrice = records.get("SalePrice");
                        BigDecimal bigSalePrice = Util.validSalePrice(salePrice);
                        if (bigSalePrice != null) {
                            item.setSalePrice(bigSalePrice);
                        } else {
                            response.setStatus(Util.ResponseFileStatus.ERROR);
                            response.setMessage("SalePrice '" + salePrice + "' is not a valid SalePrice.");
                            outputList.add(response);
                            continue;
                        }

                        // shipping price validation
                        String shippingPrice = records.get("ShippingPrice");
                        BigDecimal bigShippingPrice = Util.validShippingPrice(shippingPrice);
                        if (bigShippingPrice != null) {
                            item.setShippingPrice(bigShippingPrice);
                        } else {
                            response.setStatus(Util.ResponseFileStatus.ERROR);
                            response.setMessage("ShippingPrice '" + shippingPrice + "' is not a valid ShippingPrice.");
                            outputList.add(response);
                            continue;
                        }

                        // status validation
                        String status = records.get("Status");
                        if (Util.validStatus(status)) {
                            item.setStatus(Util.getItemStatus(status));
                        } else {
                            response.setStatus(Util.ResponseFileStatus.ERROR);
                            response.setMessage("Status '" + status + "' is not a valid Status.");
                            outputList.add(response);
                            continue;
                        }

                    } else {
                        response.setStatus(Util.ResponseFileStatus.ERROR);
                        response.setMessage("Empty values.");
                        outputList.add(response);
                        continue;
                    }

                } else {
                    response.setStatus(Util.ResponseFileStatus.ERROR);
                    response.setMessage("The line had " + records.size() + " values, we need 12.");
                    outputList.add(response);
                    continue;
                }

                // setting the remaining fields
                item.setSKU(records.get("SKU"));
                // custom made setter here
                item.setTotalItemPrice();
                BigDecimal totalValue;
                if (order.getOrderTotalValue() == null) {
                    totalValue = item.getTotalItemPrice();
                } else {
                    totalValue = order.getOrderTotalValue().add(item.getTotalItemPrice());
                }
                order.setOrderTotalValue(totalValue);

                try {
                    // if its already in the db we just need to update the order
                    if (previousOrderId != null && order.getId().equals(previousOrderId)) {
                        if (orderDao.updateOrder(order)) {
                            if (itemDao.insertItem(item)) {
                                // everything went perfectly fine
                                response.setStatus(Util.ResponseFileStatus.OK);
                                outputList.add(response);
                            }
                        }
                    } else {
                        if (orderDao.insertOrder(order)) {
                            if (itemDao.insertItem(item)) {
                                // every went perfectly fine
                                response.setStatus(Util.ResponseFileStatus.OK);
                                outputList.add(response);
                                previousOrderId = order.getId();
                            }
                        }
                    }
                } catch (SQLException e) {
                    response.setStatus(Util.ResponseFileStatus.ERROR);
                    response.setMessage(e.getMessage());
                    outputList.add(response);
                }

            }

            // close all
            csvParser.close();
            reader.close();

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return outputList;
    }
}
