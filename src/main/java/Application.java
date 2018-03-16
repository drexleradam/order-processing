import java.util.List;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {

        // what we are going to process
        Scanner scanner = new Scanner(System.in);
        System.out.print("Give the full path to the input File: ");
        String inputFilePath = scanner.nextLine();
        // our outputFile path
        System.out.print("Give the full path to the response File: ");
        String outputFilePath = scanner.nextLine();
        // where we want to upload
        System.out.print("Give the full path to the remote response File: ");
        String remoteFullFilePath = scanner.nextLine();

        scanner.close();

        OwnConnection ownConnection = new OwnConnection();

        InputFileReader reader = new InputFileReader(inputFilePath, ownConnection.getDBConnection());

        List<Response> output = reader.generateResponses();

        ownConnection.close();

        OutputFileWriter writer = new OutputFileWriter(output, outputFilePath);

        writer.writeFile();

        FtpUploader uploader = new FtpUploader(outputFilePath, remoteFullFilePath);

        uploader.upload();

    }

}
