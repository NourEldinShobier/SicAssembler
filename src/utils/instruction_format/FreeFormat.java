package utils.instruction_format;

import core.validators.ErrorController;
import utils.errors.ErrorType;

public class FreeFormat {
    private static int maxLabelLength = 8;
    private static int maxOpcodeLength = 6;
    private static int maxOperandLength = 18;

    public static String[] validate(String[] segments, int lineNumber) {
        String[] correctedSegments = new String[]{"", "", "", ""};
        if (segments.length == 1) {
            correctedSegments[2] = "";
            correctedSegments[1] = segments[0];
            correctedSegments[0] = "";
        } else if (segments.length == 2) {
            correctedSegments[2] = segments[1].startsWith(".") ? "" : segments[1];
            correctedSegments[1] = segments[0];
            correctedSegments[0] = "";
        } else if (segments.length == 3 || (segments.length >= 4 && (segments[1].startsWith(".") || segments[2].startsWith(".")))) {
            correctedSegments[3] = segments[1].startsWith(".") ? segments[1] + " " + segments[2] : segments[2].startsWith(".") ? segments[2] : "";
            correctedSegments[2] = segments[1].startsWith(".") ? "" : segments[2].startsWith(".") ? segments[1] : segments[2];
            correctedSegments[1] = (segments[2].startsWith(".") || segments[1].startsWith(".")) ? segments[0] : segments[1];
            correctedSegments[0] = (segments[2].startsWith(".") || segments[1].startsWith(".")) ? "" : segments[0];
        } else if (segments.length >= 4) {
            if (segments[3].startsWith(".")) {
                correctedSegments[3] = segments[3];
                correctedSegments[2] = segments[2];
                correctedSegments[1] = segments[1];
                correctedSegments[0] = segments[0];
            } else {
                ErrorController.getInstance().pushError(lineNumber, ErrorType.MissingMisplacedOperand);
                return null;
            }
        }
        if (validateSegmentsLength(correctedSegments, lineNumber)) return correctedSegments;
        return null;
    }

    public static boolean validateSegmentsLength(String[] segments, int lineNumber) {
        if (segments[0].length() >= maxLabelLength)
            ErrorController.getInstance().pushError(lineNumber, ErrorType.MisplacedLabel);
        else if (segments[1].length() >= maxOpcodeLength)
            ErrorController.getInstance().pushError(lineNumber, ErrorType.MissingMisplacedOperation);
        else if (segments[2].length() >= maxOperandLength)
            ErrorController.getInstance().pushError(lineNumber, ErrorType.MissingMisplacedOperand);
        else return true;
        return false;
    }
}
