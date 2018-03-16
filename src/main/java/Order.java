import java.math.BigDecimal;
import java.util.Date;

public class Order {

    private Integer id;

    private String buyerName;

    private String buyerEmail;

    private Date orderDate;

    private BigDecimal orderTotalValue;

    private String address;

    private Integer postcode;

    public Order() {
    }

    public Order(String buyerName, String buyerEmail, Date orderDate, BigDecimal orderTotalValue, String address, Integer postcode) {
        this.buyerName = buyerName;
        this.buyerEmail = buyerEmail;
        this.orderDate = orderDate;
        this.orderTotalValue = orderTotalValue;
        this.address = address;
        this.postcode = postcode;
    }

    public Order(Integer id, String buyerName, String buyerEmail, Date orderDate, BigDecimal orderTotalValue, String address, Integer postcode) {
        this.id = id;
        this.buyerName = buyerName;
        this.buyerEmail = buyerEmail;
        this.orderDate = orderDate;
        this.orderTotalValue = orderTotalValue;
        this.address = address;
        this.postcode = postcode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getOrderTotalValue() {
        return orderTotalValue;
    }

    public void setOrderTotalValue(BigDecimal orderTotalValue) {
        this.orderTotalValue = orderTotalValue;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPostcode() {
        return postcode;
    }

    public void setPostcode(Integer postcode) {
        this.postcode = postcode;
    }

}