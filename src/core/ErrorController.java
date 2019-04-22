package core;

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
    private List<ErrorRecord> errorsList;

    // Constructor
    private ErrorController() { errorsList = new ArrayList<>(); }

    // Return last error
    public ErrorRecord getLastError() {
        return errorsList.get(errorsList.size() - 1);
    }

    // Return the full list
    public List<ErrorRecord> getErrorsList() {
        return errorsList;
    }

    // Add an error to the list
    public void pushError(String address, Error error) {
        errorsList.add(new ErrorRecord(address, error.getErrorMsg()));
    }

    // Reset errorsList
    public void resetErrorList() {
        errorsList = new ArrayList<>();
    }
}
