package utils;

import core.LookupTables;
import utils.Instruction.Instruction;

public abstract class InstructionIdentifier
{
    public static Instruction identify(String line)
    {
        Instruction instruction = new Instruction();
        instruction.segments = line.split("\\s+");

        instruction = identifyLabel(instruction);
        instruction = identifyMnemonic(instruction);
        instruction = identifyOperands(instruction);

        return instruction;
    }

    private static Instruction identifyLabel(Instruction instruction){
        String label = instruction.segments[0];

        if (label.equals("")) return instruction;

        return null;
    }

    private static Instruction identifyMnemonic(Instruction instruction){

        return null;
    }
    private static Instruction identifyOperands(Instruction instruction){


        return null;
    }
}
