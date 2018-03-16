import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

    private Connection conn;

    public OrderDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Order getOrder(int id) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM doodlee_order WHERE orderid=" + id);
            if (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("orderid"));
                o.setBuyerName(rs.getString("buyername"));
                o.setBuyerEmail(rs.getString("buyeremail"));
                o.setOrderDate(rs.getDate("orderdate"));
                o.setOrderTotalValue(rs.getBigDecimal("ordertotalvalue"));
                o.setAddress(rs.getString("address"));
                o.setPostcode(rs.getInt("postcode"));
                return o;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Order> getAllOrder() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM doodlee_order");
            List<Order> list = new ArrayList<>();
            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("orderid"));
                o.setBuyerName(rs.getString("buyername"));
                o.setBuyerEmail(rs.getString("buyeremail"));
                o.setOrderDate(rs.getDate("orderdate"));
                o.setOrderTotalValue(rs.getBigDecimal("ordertotalvalue"));
                o.setAddress(rs.getString("address"));
                o.setPostcode(rs.getInt("postcode"));
                list.add(o);
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insertOrder(Order order) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO doodlee_order" +
                "(orderid, buyername, buyeremail, orderdate, ordertotalvalue, address, postcode) VALUES " +
                "(? , ? , ? , ? , ? , ? , ?)");
        ps.setInt(1, order.getId());
        ps.setString(2, order.getBuyerName());
        ps.setString(3, order.getBuyerEmail());
        ps.setDate(4, new Date(order.getOrderDate().getTime()));
        ps.setBigDecimal(5, order.getOrderTotalValue());
        ps.setString(6, order.getAddress());
        ps.setInt(7, order.getPostcode());

        int i = ps.executeUpdate();

        return i == 1;
    }

    @Override
    public boolean updateOrder(Order order) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("UPDATE doodlee_order SET " +
                "buyername = ? , buyeremail = ?, orderdate = ?, ordertotalvalue = ?, address = ?, postcode = ? WHERE orderid = ?");
        ps.setString(1, order.getBuyerName());
        ps.setString(2, order.getBuyerEmail());
        ps.setDate(3, new Date(order.getOrderDate().getTime()));
        ps.setBigDecimal(4, order.getOrderTotalValue());
        ps.setString(5, order.getAddress());
        ps.setInt(6, order.getPostcode());
        ps.setInt(7, order.getId());

        int i = ps.executeUpdate();

        return i == 1;
    }

    @Override
    public boolean deleteOrder(int id) {
        try {
            Statement stmt = conn.createStatement();
            int i = stmt.executeUpdate("DELETE FROM doodlee_order WHERE orderid= " + id);
            if (i == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
