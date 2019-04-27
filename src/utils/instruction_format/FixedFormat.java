package utils.instruction_format;

import core.validators.ErrorController;
import utils.errors.ErrorType;

public class FixedFormat {

    public static void validate(String[] segments) {
        // u can leave first operand empty for now
        // ErrorController.getInstance().pushError("", ErrorType.MissingMisplacedOperation);
        // ErrorController.getInstance().pushError("", ErrorType.MissingMisplacedOperand);
        // ErrorController.getInstance().pushError("", ErrorType.MisplacedLabel);
    }
}
