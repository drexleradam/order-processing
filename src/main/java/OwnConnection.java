import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class OwnConnection {

    private Connection connection = null;

    public Connection getDBConnection() {
        if (connection == null) {
            try {
                Properties prop = OwnProperties.getInstance();

                String url = prop.get("jdbc.url").toString();
                String user = prop.get("jdbc.user").toString();
                String password = prop.get("jdbc.password").toString();
                String className = prop.get("jdbc.driver").toString();

                Class.forName(className);

                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
