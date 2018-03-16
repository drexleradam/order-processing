import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl implements ItemDao {

    private Connection conn;

    public ItemDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Item getItem(int id) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM doodlee_order_item WHERE orderitemid=" + id);
            if (rs.next()) {
                Item i = new Item();
                i.setId(rs.getInt("orderitemid"));
                i.setOrderId(rs.getInt("orderid"));
                i.setSalePrice(rs.getBigDecimal("saleprice"));
                i.setShippingPrice(rs.getBigDecimal("shippingprice"));
                i.setTotalItemPrice(rs.getBigDecimal("totalitemprice"));
                i.setSKU(rs.getString("sku"));
                i.setStatus(Util.getItemStatus(rs.getString("status")));
                return i;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Item> getAllItem() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM doodlee_order_item");
            List<Item> list = new ArrayList<>();
            while (rs.next()) {
                Item i = new Item();
                i.setId(rs.getInt("orderitemid"));
                i.setOrderId(rs.getInt("orderid"));
                i.setSalePrice(rs.getBigDecimal("saleprice"));
                i.setShippingPrice(rs.getBigDecimal("shippingprice"));
                i.setTotalItemPrice(rs.getBigDecimal("totalitemprice"));
                i.setSKU(rs.getString("sku"));
                i.setStatus(Util.getItemStatus(rs.getString("status")));
                list.add(i);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insertItem(Item item) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO doodlee_order_item" +
                "(orderitemid, orderid, saleprice, shippingprice, totalitemprice, sku, status) VALUES " +
                "(? , ? , ? , ? , ? , ? , ?)");
        ps.setInt(1, item.getId());
        ps.setInt(2, item.getOrderId());
        ps.setBigDecimal(3, item.getSalePrice());
        ps.setBigDecimal(4, item.getShippingPrice());
        ps.setBigDecimal(5, item.getTotalItemPrice());
        ps.setString(6, item.getSKU());
        ps.setString(7, item.getStatus().toString());

        int i = ps.executeUpdate();

        return i == 1;
    }

    @Override
    public boolean updateItem(Item item) {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE doodlee_order_item SET " +
                    "orderid= ?,saleprice= ?,shippingprice= ?,totalitemprice= ?,sku= ?,status= ? WHERE orderitemid=?");
            ps.setInt(1, item.getOrderId());
            ps.setBigDecimal(2, item.getSalePrice());
            ps.setBigDecimal(3, item.getShippingPrice());
            ps.setBigDecimal(4, item.getTotalItemPrice());
            ps.setString(5, item.getSKU());
            ps.setString(6, item.getStatus().toString());
            ps.setInt(7, item.getId());

            int i = ps.executeUpdate();

            if (i == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteItem(int id) {
        try {
            Statement stmt = conn.createStatement();
            int i = stmt.executeUpdate("DELETE FROM doodlee_order_item WHERE orderitemid=" + id);
            if (i == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
