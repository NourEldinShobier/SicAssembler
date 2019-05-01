package core.validators;

import utils.errors.Error;
import utils.errors.ErrorType;

public class InstructionFormat {
    private String name;
    private String opcode;
    private boolean format2 = false;
    private boolean format34 = false;
    private boolean oneOperand = false;
    private boolean twoOperands = false;
    private boolean listOperand = false;
    private boolean isDirective = false;
    private boolean canHaveLabel = true;

    //for directives
    private boolean canHaveHexaValue = false;
    private boolean canHaveDecimalValue = false;
    private boolean canHaveHexaCharValue = false;
    private boolean canHaveLabelOperand = false;

    public InstructionFormat(String name, String opcode, boolean format2, boolean format34, boolean oneOperand,
                                boolean twoOperands, boolean listOperand) {
        this.name = name;
        this.opcode = opcode;
        this.format2 = format2;
        this.format34 = format34;
        this.oneOperand = oneOperand;
        this.twoOperands = twoOperands;
        this.listOperand = listOperand;
    }

    public InstructionFormat(String name, boolean listOperand, boolean canHaveLabel) {
        this.name = name;
        this.isDirective = true;
        this.listOperand = listOperand;
        this.canHaveLabel = canHaveLabel;
    }

    public InstructionFormat(String name, boolean listOperand,boolean oneOperand,boolean twoOperands, boolean canHaveLabel, boolean canHaveHexaValue,
                             boolean canHaveDecimalValue, boolean canHaveHexaCharValue, boolean canHaveLabelOperand) {
        this.name = name;
        this.isDirective = true;
        this.listOperand = listOperand;
        this.oneOperand = oneOperand;
        this.twoOperands = twoOperands;
        this.canHaveLabel = canHaveLabel;
        this.canHaveDecimalValue = canHaveDecimalValue;
        this.canHaveHexaValue = canHaveHexaValue;
        this.canHaveHexaCharValue = canHaveHexaCharValue;
        this.canHaveLabelOperand = canHaveLabelOperand;
    }

    public String getName() {
        return name;
    }

    public boolean isFormat2() {
        return format2;
    }

    public boolean isDirective() {
        return isDirective;
    }

    public boolean isFormat34() {
        return format34;
    }

    public boolean isListOperand() {
        return listOperand;
    }

    public boolean isOneOperand() {
        return oneOperand;
    }

    public boolean isTwoOperands() {
        return twoOperands;
    }

    public boolean canHaveLabel() {
        return canHaveLabel;
    }

    public boolean validateOperand(int lineNumber, String operand) {
        if(validateOperandsCount(lineNumber, operand)) {
            if (isDirective) return validateDirective(lineNumber, operand);
            else if (format2) return validateformat2(lineNumber, operand);
            else if (format34) return validateformat2(lineNumber, operand);
        }
        return false;
    }

    public boolean validateOperandsCount(int lineNumber,String operand) {
        String[] operands = operand.split(",");
        if(listOperand) return true;
        if(operands.length == 1 && oneOperand) return true;
        if(operands.length == 2 && twoOperands) return true;
        ErrorController.pushError(lineNumber, ErrorType.MissingMisplacedOperand);
        return false;
    }

    public  static boolean validateformat2 (int lineNumber, String operand){
        String[] reg = new String[] {"a","x","l","b","s","t","f"}; // 7 reg
        String[] Operands = operand.split(",");

        for(int i = 0; i < Operands.length; i++) {
            boolean isReg = false;
            for(int j = 0; j < reg.length; j++) {
                if(Operands[i].toLowerCase() == reg[j].toLowerCase())
                    isReg = true;
            }
            if(!isReg) {
                ErrorController.pushError(lineNumber, ErrorType.IllegalRegisterAddress);
                return false;
            }
        }

        return true;
    }

    // don't handle this case =c'aaas,adf'
    public boolean validateForamt34(int lineNumber, String operand) {
        String[] operands = operand.split(",");
        // if 1 operand
        if(operands.length == 1) {
            //if hexa literal validate
            if(operand.substring(0,2).toLowerCase().equals("x'") && operands[0].substring(operands[0].length()-1).equals("'"))
                return validateHexa(lineNumber, operands[0].substring(3, operand.length()-1));
            else
                return true;
        }
        // if 2 operands >> second one must be index register x
        if(operands.length == 2 && operands[1].toLowerCase() == "x") return true;
        return false;
    }

    public boolean validateDirective(int lineNumber, String operand) {
        // if list operand e.g word 12,34,56
        if(listOperand) {
            String[] operands = operand.split(",");
            for(int i = 0; i < operands.length; i++) {
                if(!validateDec(operands[i])){
                    ErrorController.pushError(lineNumber, ErrorType.MissingMisplacedOperand);
                    return false;
                }
            }
        }
        // if don't accept x'' and c''
        if(!canHaveHexaCharValue) {
            if(canHaveDecimalValue && validateDec(operand)) return true;
            if(canHaveHexaValue && validateHexa(lineNumber, operand)) return true;
            if(canHaveLabel) return true;
        }
        // e.g byte
        else {
            boolean endQuote = operand.substring(operand.length()-1).equals("'");
            if(operand.substring(0,2).toLowerCase().equals("x'") && endQuote)
            {
                if(operand.substring(2, operand.length()-1).length() > 2)
                {
                    ErrorController.pushError(lineNumber, ErrorType.MissingMisplacedOperand);
                    return false;
                }
                return validateHexa(lineNumber, operand.substring(2, operand.length()-1));
            }
            else if(operand.substring(0,2).toLowerCase().equals("c'") && endQuote) return true;
            else {
                ErrorController.pushError(lineNumber, ErrorType.MissingMisplacedOperand);
                return false;
            }
        }
        return false;
    }

    public boolean validateHexa(int lineNumber, String str) {
        for (int i = 1; i < str.length(); i++)
            if (Character.digit(str.charAt(i), 16) == -1) {
                ErrorController.pushError(lineNumber, ErrorType.NotHexaString);
                return false;
            }
        return true;
    }

    public static boolean validateDec(String str) {
        for (int i = 1; i < str.length(); i++)
            if (Character.digit(str.charAt(i), 10) == -1)
                return false;
        return true;
    }
}
