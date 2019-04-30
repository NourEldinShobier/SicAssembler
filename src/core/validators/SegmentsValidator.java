package core.validators;

import utils.Instruction.Instruction;
import utils.errors.ErrorType;
import utils.instruction_format.FixedFormat;
import utils.instruction_format.FreeFormat;
import utils.instruction_validators.InstructionValidator;

import java.util.ArrayList;

public class SegmentsValidator {

    private static SegmentsValidator ourInstance = new SegmentsValidator();

    public static SegmentsValidator getInstance() {
        return ourInstance;
    }

    private static Instruction instruction;
    private static Boolean freeFormat = false;
    private static ArrayList<String> symbolTable = new ArrayList<String>();

    public static void setInstruction(Instruction instruction) {
        SegmentsValidator.instruction = instruction;
    }

    public static Instruction validate() {
        // validate line format >> error[01], error[02], error[03]
        instruction.segments = validateSegments();
        if (instruction.segments == null) return null;

        // make sure opcode is valid >> error[08], error[07], error[11]
        InstructionFormat instructionFormat = validateOpcode();
        if (instructionFormat == null) return null;

        // label >> error[04], error[05]
        validateLabel(instructionFormat);

        // validate we have a valid Operand >> error[10], error[12]
        instructionFormat.validateOperand(instruction.segments[2]);

        // error[09] should be handled in pass2

        return instruction;
    }

    public static String[] validateSegments() {
        return freeFormat ? FreeFormat.validate(instruction.segments, instruction.lineNumber) :
                FixedFormat.validate(instruction.segments, instruction.lineNumber);
    }

    public static InstructionFormat validateOpcode() {
        boolean format4 = false;
        String opcode = instruction.segments[1];
        if (opcode.charAt(0) == '+') {
            format4 = true;
            opcode = opcode.substring(1);
        }
        InstructionFormat instructionFormat = InstructionSet.Search(opcode);

        if (instructionFormat == null)
            ErrorController.pushError(instruction.lineNumber, ErrorType.UnrecognizedOperation);
        else if (format4 && (instructionFormat.isFormat2() || instructionFormat.isFormat2())) {
            ErrorController.pushError(instruction.lineNumber, ErrorType.WrongPrefix);
            ErrorController.pushError(instruction.lineNumber, ErrorType.CannotBeFormatFour);
        }

        return instructionFormat;
    }

    public static boolean validateHexa(String str) {
        for (int i = 1; i < str.length(); i++)
            if (Character.digit(str.charAt(i), 16) == -1)
                return false;
        return true;
    }

    public static boolean validateLabel(InstructionFormat instructionFormat) {
        if (!instructionFormat.canHaveLabel() && instruction.segments[0] != "")
            ErrorController.pushError(instruction.lineNumber, ErrorType.StatementCannotHaveLabel);
        else if (symbolTable.contains(instruction.segments[0])) {
            if (!instruction.segments[1].toLowerCase().equals("equ"))
                ErrorController.pushError(instruction.lineNumber, ErrorType.DuplicateLabel);
        } else {
            symbolTable.add(instruction.segments[0]);
            return true;
        }
        return false;
    }
}
