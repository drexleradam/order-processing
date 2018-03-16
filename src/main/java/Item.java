import java.math.BigDecimal;

public class Item {

    private Integer id;

    private Integer orderId;

    private BigDecimal salePrice;

    private BigDecimal shippingPrice;

    private BigDecimal totalItemPrice;

    private String SKU;

    private Util.ItemStatus status;

    public Item() {
    }

    public Item(Integer orderId, BigDecimal salePrice, BigDecimal shippingPrice, BigDecimal totalItemPrice, String SKU, Util.ItemStatus status) {
        this.orderId = orderId;
        this.salePrice = salePrice;
        this.shippingPrice = shippingPrice;
        this.totalItemPrice = totalItemPrice;
        this.SKU = SKU;
        this.status = status;
    }

    public Item(Integer id, Integer orderId, BigDecimal salePrice, BigDecimal shippingPrice, BigDecimal totalItemPrice, String SKU, Util.ItemStatus status) {
        this.id = id;
        this.orderId = orderId;
        this.salePrice = salePrice;
        this.shippingPrice = shippingPrice;
        this.totalItemPrice = totalItemPrice;
        this.SKU = SKU;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(BigDecimal shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public BigDecimal getTotalItemPrice() {
        return totalItemPrice;
    }

    public void setTotalItemPrice(BigDecimal totalItemPrice) {
        this.totalItemPrice = totalItemPrice;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public Util.ItemStatus getStatus() {
        return status;
    }

    public void setStatus(Util.ItemStatus status) {
        this.status = status;
    }

    // custom setter
    public void setTotalItemPrice() {
        this.totalItemPrice = this.salePrice.add(this.shippingPrice);
    }

}