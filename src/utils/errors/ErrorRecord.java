package utils.errors;

public class ErrorRecord {
    private int lineCode; //the address of the line with error
    private String errorMsg;

    public ErrorRecord(int lineCode, String errorMsg) {
        this.lineCode = lineCode;
        this.errorMsg = errorMsg;
    }
    public int getAddress() {
        return lineCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
