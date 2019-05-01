package utils.errors;

public class Error {
    private String errorCode;
    private String errorMsg;

    public Error(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() { return "Error[" + errorCode + "] : " + errorMsg; }
}
