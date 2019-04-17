package utils;


import utils.Instruction.Instruction;
import utils.Instruction.MnemonicFormat;

abstract class FormatIdentifier
{
    static Instruction identify(Instruction instruction)
    {
        if (isStartEnd(instruction)) instruction.isStartEnd = true;
        if (isDirective(instruction)) instruction.isDirective = false;

        if (!instruction.isDirective && !instruction.isStartEnd)
            instruction = diagnoseFormat(instruction);

        return instruction;
    }

    private static boolean isStartEnd(Instruction instruction)
    {
        return (instruction.segments[1].contains("START") ||
                instruction.segments[1].contains("END"));
    }

    private static boolean isDirective(Instruction instruction)
    {
        return (instruction.segments[1].contains("RESW") ||
                instruction.segments[1].contains("RESB") ||
                instruction.segments[1].contains("WORD") ||
                instruction.segments[1].contains("BYTE"));
    }

    private static Instruction diagnoseFormat(Instruction instruction)
    {
        if (instruction.segments[2].equals(""))
            instruction.mnemonic.format = MnemonicFormat.ONE;

        else if (instruction.segments[2].contains(","))
            instruction.mnemonic.format = MnemonicFormat.TWO;

        else if (instruction.segments[1].startsWith("+"))
            instruction.mnemonic.format = MnemonicFormat.FOUR;
        else
            instruction.mnemonic.format = MnemonicFormat.THREE;

        return instruction;
    }
}
