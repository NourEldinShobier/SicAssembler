package utils.instruction_validators;

public class InstructionValidator {
    private String name;
    private String opcode;
    private boolean format2;
    private boolean format34;
    private boolean oneOperand;
    private boolean twoOperands;
    private boolean listOperand;
    private boolean isDirective;

    public InstructionValidator(String name, String opcode, boolean format2, boolean format34, boolean oneOperand,
                             boolean twoOperands, boolean listOperand, boolean isDirective) {
        this.name = name;
        this.opcode = opcode;
        this.format2 = format2;
        this.format34 = format34;
        this.oneOperand = oneOperand;
        this.twoOperands = twoOperands;
        this.listOperand = listOperand;
        this.isDirective = isDirective;
    }


    public boolean validate(String label, String operand) {
        return validateLabel(label) && validateOperand(operand);
    }

    public boolean validateLabel(String label) {
        return false;
    }

    public boolean validateOperand(String operand) {
        return false;
    }
}
