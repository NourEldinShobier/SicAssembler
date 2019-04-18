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

        if (label.equals("")) return instruction;

        // CONVERT PC TO HEX
        LookupTables.symbolTable.put(label, Integer.toString(PC));

        return instruction;
    }

    private static Instruction identifyMnemonic(Instruction instruction)
    {
        int length = 0;

        if (instruction.isStartEnd) initPC(instruction);

        else if (instruction.isDirective) {
            //HERE


            PC += length;
        }
        else {
            if (instruction.mnemonic.format == MnemonicFormat.TWO) length = 2;
            if (instruction.mnemonic.format == MnemonicFormat.THREE) length = 3;
            if (instruction.mnemonic.format == MnemonicFormat.FOUR) length = 4;

            // CONVERT PC TO HEX
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
