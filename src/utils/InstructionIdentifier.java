package utils;

import core.LookupTables;
import utils.Instruction.Instruction;
import utils.Instruction.MnemonicFormat;

public abstract class InstructionIdentifier
{
    private static int PC = -1;

    public static Instruction identify(String line)
    {
        Instruction instruction = new Instruction();
        instruction.segments = line.split("\\s+");

        instruction = FormatIdentifier.identify(instruction);

        assert instruction != null;

        instruction = identifyLabel(instruction);
        instruction = identifyMnemonic(instruction);
        instruction = identifyOperands(instruction);

        return instruction;
    }

    private static Instruction identifyLabel(Instruction instruction)
    {
        String label = instruction.segments[0];
        //if there's no label, then return instruction
        if (label.equals("")) return instruction;

        return instruction;
    }

    private static Instruction identifyMnemonic(Instruction instruction)
    {
        int length = 0;

        if (instruction.isStartEnd) initPC(instruction);

        else if (instruction.isDirective) {
            // Notice annotations X,C

            PC += length;
        }
        else {
            if (instruction.mnemonic.format == MnemonicFormat.ONE) length = 8;
            if (instruction.mnemonic.format == MnemonicFormat.TWO) length = 16;
            if (instruction.mnemonic.format == MnemonicFormat.THREE) length = 24;
            if (instruction.mnemonic.format == MnemonicFormat.FOUR) length = 32;

            instruction.memoryLocation = Integer.toString(PC);
            PC += length;
        }

        return instruction;
    }

    private static Instruction identifyOperands(Instruction instruction)
    {
        //....
        return instruction;
    }

    private static void initPC(Instruction instruction)
    {
        if (instruction.segments[1].equals("START"))
            PC = Integer.parseInt(instruction.segments[2]);
    }
}
