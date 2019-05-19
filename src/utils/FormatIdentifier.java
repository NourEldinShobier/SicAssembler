package utils;


import core.InstructionSet;
import utils.Instruction.Instruction;
import utils.Instruction.Mnemonic;
import utils.Instruction.MnemonicFormat;

abstract class FormatIdentifier
{
    static Instruction identify(Instruction instruction)
    {
        if (isStartEnd(instruction)) instruction.isStartEnd = true;
        if (isDirective(instruction)) instruction.isDirective = true;

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
                instruction.segments[1].contains("BYTE") ||
                instruction.segments[1].contains("ORG") ||
                instruction.segments[1].contains("EQU") ||
                instruction.segments[1].contains("LTORG"));

    }

    private static Instruction diagnoseFormat(Instruction instruction)
    {
        if (instruction.segments[1].startsWith("+"))
        {
            instruction.mnemonic.format = MnemonicFormat.FOUR;
            return instruction;
        }

        for (Mnemonic mnemonic : InstructionSet.oneOperandMnemonics){
            if(mnemonic.name.equals(instruction.segments[1])){
                instruction.mnemonic.format = MnemonicFormat.THREE;
                return  instruction;
            }
        }

        instruction.mnemonic.format = MnemonicFormat.TWO;
        return  instruction;
    }
}
