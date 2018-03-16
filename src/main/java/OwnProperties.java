import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class OwnProperties {
    private static Properties prop = null;

    public static Properties getInstance() {
        if (prop == null) {
            prop = new Properties();

            try {
                prop.load(new FileInputStream(System.getProperty("ownPropPath")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return prop;
    }

    private OwnProperties() {
    }
}
