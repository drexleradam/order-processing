import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class OutputFileWriter {

    private List<Response> responseList;
    private String outputFilePath;

    public OutputFileWriter(List<Response> responseList, String outputFilePath) {
        this.responseList = responseList;
        this.outputFilePath = outputFilePath;
    }

    public void writeFile() {
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFilePath));
            CSVFormat ownFormat = CSVFormat.newFormat(';');
            CSVPrinter csvPrinter = new CSVPrinter(writer, ownFormat
                    .withHeader("LineNumber", "Status", "Message")
                    .withRecordSeparator("\r\n"));

            for (Response r : responseList) {
                csvPrinter.printRecord(r.getList());
                csvPrinter.flush();
            }

            csvPrinter.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
