package utils.instruction_format;

import core.validators.ErrorController;
import utils.errors.ErrorType;

import java.lang.reflect.Array;

public class FixedFormat {
    private static int BLANK_LABEL = 8;
    private static int BLANK_OPCODE = 6;
    private static String[] parts;
    private static String[] CorrectedSegments = new String[4];

    public static String[] validate(String[] segments, int lineNumber) {
        CorrectedSegments = new String[4];
        if (validateLabel(segments[0], lineNumber) != null)
            if (validateOpcode(segments[1], lineNumber) != null)
                if (validateOperand(segments[2], lineNumber) != null)
                    if (validteComment(segments[3], lineNumber) != null)
                        return CorrectedSegments;
        return null;
    }

    /********************LABEL**************************/
    /**
     * label segment validation (1->8 label) (9 blank)
     */
    private static String validateLabel(String label, int lineNumber) {
        /* handling space before label - ( LABEL)*/
        if (label.startsWith(" ") && !label.trim().isEmpty()) // label
        {
            ErrorController.getInstance().pushError(lineNumber, ErrorType.MisplacedLabel);
            CorrectedSegments[0] = null;
        }
        parts = label.split("//s+");

        /* handling spaces between label characters - (LA BE L)*/
        if (parts.length > 1) {
            CorrectedSegments[0] = null;
            ErrorController.getInstance().pushError(lineNumber, ErrorType.UndefinedSymbol);
        }
        //correct
        else {
            CorrectedSegments[0] = label.split("//s+")[0];
        }

        /* handling blank character post label not left empty - (LABELMOV)*/
        if (label.charAt(BLANK_LABEL) != ' ') {
            ErrorController.getInstance().pushError(lineNumber, ErrorType.MissingMisplacedOperation);
            CorrectedSegments[0] = null;
        }

        return CorrectedSegments[0];
    }

    /***********************MNEMONIC****************************/
    /**
     * Opcode segment validation (10 -> 15 opcode) (16->17 blank)
     */
    public static String validateOpcode(String opcode, int lineNumber) {
        /* handling space before mnemonic - ( ADD) */
        CorrectedSegments[1] = opcode.split("//s+")[0];

        if (opcode.startsWith(" ")) {

            ErrorController.getInstance().pushError(lineNumber, ErrorType.MissingMisplacedOperation);
            CorrectedSegments[1] = null;

        }
        /* handling spaces between mnemonic characters - (AD D)*/

        parts = opcode.split("//s+");
        if (parts.length > 1) {
            ErrorController.getInstance().pushError(lineNumber, ErrorType.UnrecognizedOperation);
            CorrectedSegments[1] = null;
        }


        /* checking that two characters are left blank post mnemonic*/
        if (opcode.charAt(BLANK_OPCODE) != ' ' || opcode.charAt(BLANK_OPCODE + 1) != ' ') {
            ErrorController.getInstance().pushError(lineNumber, ErrorType.MissingMisplacedOperand);
            CorrectedSegments[1] = null;

        }

        return CorrectedSegments[1];
    }

    /*****************************OPERANDS***********************************/
    /**
     * Operands validation (18 -> 35 operands)
     */
    public static String validateOperand(String operand, int lineNumber) {
        /* handling spaces before operands (    T,S)*/
        CorrectedSegments[2] = operand.split("//s+")[0];
        if (operand.startsWith(" ") && !operand.trim().isEmpty()) {
            ErrorController.getInstance().pushError(lineNumber, ErrorType.MissingMisplacedOperand);
            CorrectedSegments[2] = null;
        }
        /* handling missing operand or more-than-two operands(A, ) OR (A,B,C)*/
        if (operand.contains(",")) {
            //case - add a,b,c
            if (operand.substring(operand.indexOf(",") + 1).contains(",")) {

                ErrorController.getInstance().pushError(lineNumber, ErrorType.UndefinedSymbol);
                CorrectedSegments[2] = null;
            }

            if (operand.substring(operand.indexOf(",") + 1).isBlank()) {
                ErrorController.getInstance().pushError(lineNumber, ErrorType.MissingMisplacedOperand);
                CorrectedSegments[2] = null;
            }

        }

        /* handling storage / constant definition*/
        if (operand.contains("C'") || operand.contains("X'") || operand.contains("x'") || operand.contains("c'")) {
            //missing end quote
            if (!operand.substring(2).contains("'")) {
                ErrorController.getInstance().pushError(lineNumber, ErrorType.MissingMisplacedOperand);
                CorrectedSegments[2] = null;

            } else {
                //multiple quotes
                int endingQuote = operand.indexOf("'", operand.indexOf("'") + 1);
                if (operand.substring(endingQuote + 1).contains(",")) {
                    ErrorController.getInstance().pushError(lineNumber, ErrorType.UndefinedSymbol);
                    CorrectedSegments[2] = null;

                }
            }
        }

        return CorrectedSegments[2];
    }

    /*********************COMMENT***************/
    /**
     * Comment validating (36 -> 66)
     */
    public static String validteComment(String comment, int lineNumber) {
        /*validating comment starts with a point*/
        if (comment.trim().isBlank()) CorrectedSegments[3] = "";
        if (!comment.trim().isEmpty()) {
            if (!comment.startsWith(".")) {
                ErrorController.getInstance().pushError(lineNumber, ErrorType.UndefinedSymbol);
                CorrectedSegments[3] = null;
            } else {
                CorrectedSegments[3] = comment;
            }
        }

        return CorrectedSegments[3];
    }
}
