package utils;

import core.LookupTables;
import core.Settings;
import utils.Instruction.Instruction;
import utils.Instruction.MnemonicFormat;

public abstract class InstructionIdentifier
{
    private static int PC = -1;

    public static Instruction identify(Instruction instruction)
    {
        instruction = FormatIdentifier.identify(instruction);

        for (int i = 0; i < instruction.segments.length; i++)
            instruction.segments[i] = instruction.segments[i].trim();


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

        if (instruction.isStartEnd)
            instruction = diagnoseStartEnd(instruction);

        else if (instruction.isDirective) {

            switch (instruction.segments[1]) {
                case "BYTE":
                    length = diagnoseBYTE(instruction.segments[2]);
                    break;
                case "WORD":
                    length = diagnoseWORD(instruction.segments[2]);
                    break;
                case "RESB":
                    length = Integer.parseInt(instruction.segments[2]);
                    break;
                case "RESW":
                    length = 3 * Integer.parseInt(instruction.segments[2]);
                    break;
                case "ORG":
                    PC = Integer.parseInt(instruction.segments[2]);
                    break;
                case "EQU":
                    diagnoseEQU(instruction);
                    break;
            }

            instruction.memoryLocation = Integer.toHexString(PC);
            PC += length;
        }
        else {
            if (instruction.mnemonic.format == MnemonicFormat.TWO) length = 2;
            if (instruction.mnemonic.format == MnemonicFormat.THREE) length = 3;
            if (instruction.mnemonic.format == MnemonicFormat.FOUR) length = 4;

            instruction.memoryLocation = Integer.toHexString(PC);
            PC += length;

            // PASS 2 - CODE: Identify Mnemonic from instruct set
        }

        return instruction;
    }

    private static Instruction identifyOperands(Instruction instruction)
    {
        //....
        return instruction;
    }

    private static Instruction diagnoseStartEnd(Instruction instruction)
    {
        if (instruction.segments[1].equals("START")) {
            Settings.startLabel = instruction.segments[0].trim();
            Settings.startOperand = instruction.segments[2].trim();
            PC = Integer.parseInt(instruction.segments[2], 16);
            instruction.memoryLocation = Integer.toHexString(
                    Integer.parseInt(instruction.segments[2], 16)
            );
        }
        else instruction.memoryLocation = Integer.toHexString(PC);

        return instruction;
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
    {
        int length = 1;

        if (operand.contains(",")) {
            int i = 0, CommaCount = 0;

            while (i < operand.length()) {
                if (operand.charAt(i) == ',') CommaCount++;

                i++;
            }

            length = CommaCount + 1;
        }

        return 3 * length;
    }

    private static void diagnoseEQU(Instruction instruction){

        boolean isHex = instruction.segments[2].matches("^[0-9a-fA-F]+$");

        if (isHex){
            LookupTables.symbolTable.put(instruction.segments[0], instruction.segments[2]);
            return;
        }

        String memoryLocation = LookupTables.symbolTable.get(instruction.segments[2]);

        LookupTables.symbolTable.put(instruction.segments[0], memoryLocation);
    }
}
