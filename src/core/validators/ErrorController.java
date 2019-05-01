package core.validators;

import java.util.ArrayList;
import java.util.List;

import utils.errors.*;
import utils.errors.Error;

public class ErrorController {
    // ErrorController uses singleton design pattern
    private static ErrorController ourInstance = new ErrorController();
    public static ErrorController getInstance() {
        return ourInstance;
    }

    // List of errors in the input code
    // ErrorRecord : {String address, String errorMsg}
    private static List<ErrorRecord> errorsList;

    // Constructor
    private ErrorController() { errorsList = new ArrayList<>(); }

    public boolean foundErrors(int lineNumber) {
        return getErrorList(lineNumber).size() != 0;
    }

    // Return last error
    public ErrorRecord getLastError() {
        return errorsList.get(errorsList.size() - 1);
    }

    // Return the full list
    public List<ErrorRecord> getErrorsList() {
        return errorsList;
    }

    // Add an error to the list
    public static void pushError(int lineNumber, Error error) {
        errorsList.add(new ErrorRecord(lineNumber, error.getErrorMsg()));
    }

    // Reset errorsList
    public static void resetErrorList() {
        errorsList = new ArrayList<>();
    }

    public List<ErrorRecord> getErrorList(int lineNumber) {
        List<ErrorRecord> result = new ArrayList<>();
        for (ErrorRecord err: errorsList) {
            if (err.getAddress() == lineNumber) {
                result.add(err);
            }
        }
        return result;
    }
}
