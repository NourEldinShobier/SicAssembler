package utils.instruction_format;

import core.validators.ErrorController;
import utils.errors.ErrorRecord;
import utils.errors.ErrorType;

public class FixedFormat {
    private static int BLANK_LABEL = 8;
    private static int BLANK_OPCODE = 15;
    private static String [] parts;
    private static String [] CorrectedSegments;

    public static String[] validate(String[] segments) {

        // u can leave first operand empty for now
        // ErrorController.getInstance().pushError("", ErrorType.MissingMisplacedOperation);
        // ErrorController.getInstance().pushError("", ErrorType.MissingMisplacedOperand);
        // ErrorController.getInstance().pushError("", ErrorType.MisplacedLabel);
        /********************LABEL**************************/
        /** label segment validation (1->8 label) (9 blank)*/
        /* handling space before label - ( LABEL)*/
            if(segments[0].startsWith(" ")) // label
            {
                ErrorController.getInstance().pushError("", ErrorType.MisplacedLabel);
                CorrectedSegments[0] = null;
            }
            parts = segments[0].split("//s+");

            /* handling spaces between label characters - (LA BE L)*/
            if(parts.length > 1){
                    CorrectedSegments[0] = null;
                    ErrorController.getInstance().pushError("", ErrorType.UndefinedSymbol);
            }
            //correct
            else{
                CorrectedSegments[0] = segments[0].split("//s+")[0];
            }

            /* handling blank character post label not left empty - (LABELMOV)*/
            if(segments[0].charAt(BLANK_LABEL) != ' '){
                ErrorController.getInstance().pushError("", ErrorType.MissingMisplacedOperation);
                CorrectedSegments[0] = null;

            }

            /***********************MNEMONIC****************************/
            /** Opcode segment validation (10 -> 15 opcode) (16->17 blank)*/
            /* handling space before mnemonic - ( ADD) */
            CorrectedSegments[1] = segments[1].split("//s+")[0];

            if(segments[1].startsWith(" ")){

                ErrorController.getInstance().pushError("", ErrorType.MissingMisplacedOperation);
                CorrectedSegments[1]= null;

            }
            /* handling spaces between mnemonic characters - (AD D)*/

                parts = segments[1].split("//s+");
                if(parts.length > 1) {
                    ErrorController.getInstance().pushError("", ErrorType.UnrecognizedOperation);
                    CorrectedSegments[1] = null;
                }


            /* checking that two characters are left blank post mnemonic*/
            if(segments[1].charAt(BLANK_OPCODE) != ' ' || segments[1].charAt(BLANK_OPCODE+1) != ' ' ){
                ErrorController.getInstance().pushError("", ErrorType.MissingMisplacedOperand);
                CorrectedSegments[1] = null;

            }

            /*****************************OPERANDS***********************************/
            /** Operands validation (18 -> 35 operands)*/
            /* handling spaces before operands (    T,S)*/
        CorrectedSegments[2] = segments[2].split("//s+")[0];
        if(segments[2].startsWith(" ")){
                ErrorController.getInstance().pushError("", ErrorType.MissingMisplacedOperand);
                CorrectedSegments[2] = null;
            }
            /* handling missing operand or more-than-two operands(A, ) OR (A,B,C)*/
            if(segments[2].contains(",")){
                //case - add a,b,c
                if(segments[2].substring(segments[2].indexOf(",")+1).contains(",")) {

                    ErrorController.getInstance().pushError("", ErrorType.UndefinedSymbol);
                    CorrectedSegments[2] = null;
                }

                    if(segments[2].substring(segments[2].indexOf(",")+1).isBlank()) {
                        ErrorController.getInstance().pushError("", ErrorType.MissingMisplacedOperand);
                        CorrectedSegments[2] = null;
                    }

            }

            /* handling storage / constant definition*/
            if(segments[2].contains("C'") || segments[2].contains("X'") || segments[2].contains("x'") || segments[2].contains("c'")) {
                //missing end quote
                if (!segments[2].substring(2).contains("'"))
                {
                    ErrorController.getInstance().pushError("", ErrorType.MissingMisplacedOperand);
                    CorrectedSegments[2] = null;

                }
                else {
                    //multiple quotes
                    int endingQuote = segments[2].indexOf("'", segments[2].indexOf("'") + 1);
                    if(segments[2].substring(endingQuote+1).contains(",")){
                        ErrorController.getInstance().pushError("", ErrorType.UndefinedSymbol);
                        CorrectedSegments[2] = null;

                    }
                }
            }
            /*********************COMMENT***************/
            /** Comment validating (36 -> 66)*/
            /*validating comment starts with a point*/
        if(!segments[3].isBlank())          CorrectedSegments[3] = " ";
        if(!segments[3].isEmpty()){
                if(!segments[3].startsWith(".")){
                   ErrorController.getInstance().pushError("", ErrorType.UndefinedSymbol);
                    CorrectedSegments[3] = null;}
                else{
                    CorrectedSegments[3] = segments[3];
                }

        }
           return CorrectedSegments;

    }
}
