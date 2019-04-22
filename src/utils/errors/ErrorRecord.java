package utils.errors;

public class ErrorRecord {
    private String address; //the address of the line with error
    private String errorMsg;

    public ErrorRecord(String address, String errorMsg) {
        this.address = address;
        this.errorMsg = errorMsg;
    }

    public String getAddress() {
        return address;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
