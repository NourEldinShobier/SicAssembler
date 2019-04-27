package utils.instruction_validators;

public class InstructionValidator {
    private boolean isDirective;
    private boolean canBeFormatFour;
    private boolean canHaveLabel;
    private boolean canReuseLabel; // e.g. EQU

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
