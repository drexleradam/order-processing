import java.util.ArrayList;
import java.util.List;

public class Response {

    private Integer lineNumber;

    private Util.ResponseFileStatus status;

    private String message;

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Util.ResponseFileStatus getStatus() {
        return status;
    }

    public void setStatus(Util.ResponseFileStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getList() {
        List<String> list = new ArrayList<>();
        if (getLineNumber() == null) {
            list.add("no line number");
        } else {
            list.add(getLineNumber().toString());
        }
        list.add(getStatus().toString());
        list.add(getMessage());
        return list;
    }
}
