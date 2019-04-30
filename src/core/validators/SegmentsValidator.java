package core.validators;

import utils.Instruction.Instruction;
import utils.instruction_format.FixedFormat;
import utils.instruction_format.FreeFormat;
import utils.instruction_validators.InstructionValidator;

public class SegmentsValidator {

    private static SegmentsValidator ourInstance = new SegmentsValidator();
    public static SegmentsValidator getInstance() {
        return ourInstance;
    }

    private static Instruction instruction;
    private static Boolean freeFormat = false;

    public static void setInstruction(Instruction instruction) {
        SegmentsValidator.instruction = instruction;
    }

    public void validate() {
        // validate line format >> error[01], error[02], error[03]
        validateSegments();

        // make sure opcode is valid >> error[08], error[07], error[11]
        validateOpcode();

        // validate we have a valid Operand >> error[10], error[12]
        validateOperand();

        // label >> error[04], error[05]
        validateLabel();

        // error[09] should be handled in pass2
    }

    public static void validateSegments() {
        if (freeFormat) FreeFormat.validate(instruction.segments,instruction.lineNumber);
        else FixedFormat.validate(instruction.segments);
    }

    public static void validateOpcode() {

    }

    public void validateOperand() {
        //if(this.instruction.segments[2])
        validateHexa();
    }

    public static void validateLabel() {

    }

    public static void validateHexa() {

    }
}
