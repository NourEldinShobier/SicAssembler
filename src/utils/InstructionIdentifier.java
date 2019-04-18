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

        LookupTables.symbolTable.put(label, Integer.toHexString(PC));

        return instruction;
    }

    private static Instruction identifyMnemonic(Instruction instruction)
    {
        int length = 0;

        if (instruction.isStartEnd) initPC(instruction);

        else if (instruction.isDirective) {

            if (instruction.segments[1].equals("BYTE"))
                length = diagnoseBYTE(instruction.segments[2]);
            else if (instruction.segments[1].equals("ResB"))
                length = Integer.parseInt(instruction.segments[2]);
            else if (instruction.segments[1].equals("ResW"))
                length = 3*Integer.parseInt(instruction.segments[2]);
            else if (instruction.segments[1].equals("WORD"))
               length = diagnoseWORD(instruction.segments[2]);

            instruction.memoryLocation = Integer.toHexString(PC);
            PC += length;
        }
        else {
            if (instruction.mnemonic.format == MnemonicFormat.TWO) length = 2;
            if (instruction.mnemonic.format == MnemonicFormat.THREE) length = 3;
            if (instruction.mnemonic.format == MnemonicFormat.FOUR) length = 4;

            instruction.memoryLocation = Integer.toHexString(PC);
            PC += length;

            // PASS 2 - CODE
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
            PC = Integer.parseInt(instruction.segments[2], 16);
    }

    private static int diagnoseBYTE(String operand)
    {
        int length = 0;

        if (operand.startsWith("C")) {
            String data = operand.substring(2, operand.length() - 1);
            length = data.length();
        }

        else if (operand.startsWith("X")) {
            String data = operand.substring(2, operand.length() - 1);
            length = data.length() / 2;
        }

        return length;
    }
    private static int diagnoseWORD(String operand)
    { int length = 1;

      if(operand.contains(",")){
       int i = 0,CommaCount = 0;

       while(i < operand.length()){

           if(operand.charAt(i) == ','){
               CommaCount++;
           }

           i++;
       }
       length = CommaCount +1;
     }


     return 3*length;
    }
}
