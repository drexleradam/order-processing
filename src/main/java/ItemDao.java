import java.sql.SQLException;
import java.util.List;

public interface ItemDao {
    Item getItem(int id);

    List<Item> getAllItem();

    boolean insertItem(Item item) throws SQLException;

    boolean updateItem(Item item);

    boolean deleteItem(int id);
}
