import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FtpUploader {

    private String ownFullFilePath;
    private String remoteFullFilePath;

    private FTPClient client;

    public FtpUploader(String ownFullFilePath, String remoteFullFilePath) {
        this.ownFullFilePath = ownFullFilePath;
        this.remoteFullFilePath = remoteFullFilePath;
    }

    public void upload() {
        try {
            Properties prop = OwnProperties.getInstance();

            client = new FTPClient();

            client.connect(prop.getProperty("ftp.server"), Integer.valueOf(prop.getProperty("ftp.port")));
            client.login(prop.getProperty("ftp.user"), prop.getProperty("ftp.password"));
            client.enterLocalPassiveMode();

            client.setFileType(FTP.BINARY_FILE_TYPE);
            File file = new File(ownFullFilePath);

            InputStream inputStream = new FileInputStream(file);

            if (client.storeFile(remoteFullFilePath, inputStream)) {
                System.out.println("Uploading succeed!");
            } else {
                System.out.println("Uploading failed!");
            }
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (client.isConnected()) {
                    client.logout();
                    client.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
