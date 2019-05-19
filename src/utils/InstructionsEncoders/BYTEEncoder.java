package utils.InstructionsEncoders;

import utils.Instruction.Instruction;

public class BYTEEncoder {
    public static Instruction encode(Instruction instruction) {
        String operand = instruction.segments[2];
        String opcodeHEX;
        StringBuilder builder = new StringBuilder();
        int end = operand.length() - 1;

        if (operand.startsWith("X")) {
            operand = operand.substring(2, end);
            char[] ch = operand.toCharArray();
            for (char c : ch) {
                builder.append(c);
            }
            operand = builder.toString();

        } else {// operand starts with "C"

            operand = operand.substring(2, end);

            char[] ch = operand.toCharArray();

            for (char c : ch) {
                String hex = String.format("%H", c);
                builder.append(hex);
            }
            operand = builder.toString();
        }
        opcodeHEX = operand;
        // Standard Opcode
        StringBuilder stringBuilder = new StringBuilder(opcodeHEX);

        instruction.opCode = stringBuilder.toString().toUpperCase();
        return instruction;
    }
}
