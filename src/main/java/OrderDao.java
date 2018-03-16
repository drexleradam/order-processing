import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    Order getOrder(int id);

    List<Order> getAllOrder();

    boolean insertOrder(Order order) throws SQLException;

    boolean updateOrder(Order order) throws SQLException;

    boolean deleteOrder(int id);
}
