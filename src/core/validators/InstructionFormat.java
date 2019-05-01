package core.validators;

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

    public boolean validateOperand(String operand) {
        return true;
    }
}
