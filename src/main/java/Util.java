import org.apache.commons.validator.routines.BigDecimalValidator;
import org.apache.commons.validator.routines.DateValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.IntegerValidator;

import java.math.BigDecimal;
import java.util.Map;

public abstract class Util {

    public enum ItemStatus {
        IN_STOCK,
        OUT_OF_STOCK
    }

    public enum ResponseFileStatus {
        OK,
        ERROR
    }

    public static ItemStatus getItemStatus(String status) {
        switch (status) {
            case "IN_STOCK":
                return ItemStatus.IN_STOCK;
            case "OUT_OF_STOCK":
                return ItemStatus.OUT_OF_STOCK;
            default:
                return null;
        }
    }

    /**
     * Validates a string value if it is a real email address or not.
     *
     * @param email which must be validated
     * @return true if its valid
     */
    public static boolean validEmail(String email) {

        return EmailValidator.getInstance().isValid(email);

    }

    /**
     * Validates records if they are empty.
     *
     * @param map with must be validated
     * @return true if no field is empty
     */
    public static boolean noEmptyFields(Map<String, String> map) {
        map.remove("OrderDate");
        return !map.containsValue("");
    }

    /**
     * Checks the OrderDate record, if it is empty or not.
     *
     * @param orderDate with must be checked
     * @return true if its empty
     */
    public static boolean emptyOrderDate(String orderDate) {
        return orderDate.isEmpty();
    }

    /**
     * Validates the orderDate, if it can parse it to a Date, with the given datePattern.
     *
     * @param orderDate   which must be validated
     * @param datePattern date pattern
     * @return true if its valid
     */
    public static boolean validOrderDate(String orderDate, String datePattern) {

        return DateValidator.getInstance().isValid(orderDate, datePattern);

    }

    /**
     * Validating if the given postCode is an Integer and is bigger than 0.
     *
     * @param postCode which must be validated
     * @return true if its valid
     */
    public static boolean validPostcode(String postCode) {
        Integer integer = IntegerValidator.getInstance().validate(postCode);

        return integer != null && IntegerValidator.getInstance().minValue(integer, 1);

    }

    /**
     * Validating price if it is a BigDecimal, positive and the minimum value is 0.00 .
     *
     * @param price which must be validated
     * @return the number, if its valid
     * null if not valid
     */
    public static BigDecimal validShippingPrice(String price) {
        BigDecimal num = BigDecimalValidator.getInstance().validate(price);
        if (num != null) {
            if (BigDecimalValidator.getInstance().minValue(num, 0.00)) {
                return num;
            }
        }
        return null;
    }

    /**
     * Validating price if it is a BigDecimal, positive and the minimum value is 1.00 .
     *
     * @param price which must be validated
     * @return the number, if its valid
     * null if not valid
     */
    public static BigDecimal validSalePrice(String price) {
        BigDecimal num = BigDecimalValidator.getInstance().validate(price);
        if (num != null) {
            if (BigDecimalValidator.getInstance().minValue(num, 1.00)) {
                return num;
            }
        }
        return null;
    }

    /**
     * Validates the status, if it is in the ItemStatus enum or not.
     *
     * @param status which must be validated
     * @return true if its valid
     */
    public static boolean validStatus(String status) {
        for (ItemStatus s : ItemStatus.values()) {
            if (status.equals(s.toString())) {
                return true;
            }
        }
        return false;
    }
}
