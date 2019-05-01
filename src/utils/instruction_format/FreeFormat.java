package utils.instruction_format;

import core.validators.ErrorController;
import utils.errors.ErrorType;

public class FreeFormat {
    private static int maxLabelLength = 8;
    private static int maxOpcodeLength = 6;
    private static int maxOperandLength = 18;

    public static String[] validate(String[] segments, int lineNumber) {
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
            return null;
        }
        if(validateSegmentsLength(segments, lineNumber)) return segments;
        return null;
    }

    public static boolean validateSegmentsLength(String[] segments, int lineNumber) {
        if(segments[0].length() >= maxLabelLength)
            ErrorController.getInstance().pushError(lineNumber, ErrorType.MisplacedLabel);
        else if(segments[0].length() >= maxOpcodeLength)
            ErrorController.getInstance().pushError(lineNumber, ErrorType.MissingMisplacedOperation);
        else if(segments[0].length() >= maxOperandLength)
            ErrorController.getInstance().pushError(lineNumber, ErrorType.MissingMisplacedOperand);
        else return true;
        return false;
    }
}
