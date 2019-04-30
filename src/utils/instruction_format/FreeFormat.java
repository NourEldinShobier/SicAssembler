package utils.instruction_format;

import core.validators.ErrorController;
import utils.errors.ErrorType;

public class FreeFormat {
    private static int maxLabelLength = 9;
    private static int maxOpcodeLength = 5;
    private static int maxOperandLength = 10;

    public static String[] validate(String[] segments, int lineNumber) {
        // u can leave first operand empty for now
        // ErrorController.getInstance().pushError("", ErrorType.MissingMisplacedOperation);
        // ErrorController.getInstance().pushError("", ErrorType.MissingMisplacedOperand);
        // ErrorController.getInstance().pushError("", ErrorType.MisplacedLabel);
        if( segments.length == 1 ) {
            segments[2] = "";
            segments[1] = segments[0];
            segments[0] = "";
        }
        else if( segments.length == 2 ) {
            segments[2] = segments[1];
            segments[1] = segments[0];
            segments[0] = "";
        }
        else if( segments.length >= 4 ) {
            ErrorController.getInstance().pushError(lineNumber, ErrorType.MissingMisplacedOperand);
        }
        validateSegmentsLength(segments, lineNumber);
        return segments;
    }

    public static void validateSegmentsLength(String[] segments, int lineNumber) {
        if(segments[0].length() >= maxLabelLength)
            ErrorController.getInstance().pushError(lineNumber, ErrorType.MisplacedLabel);
        else if(segments[0].length() >= maxOpcodeLength)
            ErrorController.getInstance().pushError(lineNumber, ErrorType.MissingMisplacedOperation);
        else if(segments[0].length() >= maxOperandLength)
            ErrorController.getInstance().pushError(lineNumber, ErrorType.MissingMisplacedOperand);
    }
}
