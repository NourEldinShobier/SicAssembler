package utils.InstructionsEncoders;

import core.InstructionSet;
import core.LookupTables;
import utils.Instruction.Instruction;
import utils.Instruction.Mnemonic;
import utils.Instruction.MnemonicFormat;

public class FormatTHREEEncoder {
    public static Instruction encode(Instruction instruction) {
        if (instruction.segments[1].equals("J") && instruction.segments[2].equals("*"))
            return diagnoseJStar(instruction);

        else {
            instruction.mnemonic = diagnoseMnemonic(instruction.segments[1]);

            String nixbpe = diagnoseNIXBPE(instruction.segments[2]);
            String ta = diagnoseTA(instruction.segments[2]);

            int disp = Integer.parseInt(ta) - (Integer.parseInt(instruction.memoryLocation, 16));

            String opCodeBinary = standardOpCode(instruction.mnemonic.opCode) + nixbpe + standardDisp(Integer.toString(disp));
            String opCodeHEX = Integer.toString(Integer.parseInt(opCodeBinary, 2), 16);

            // Standard Opcode
            StringBuilder stringBuilder = new StringBuilder(opCodeHEX);
            while (stringBuilder.length() < 6) stringBuilder.insert(0, "0");

            instruction.opCode = stringBuilder.toString().toUpperCase();

        }
        return instruction;
    }

    private static Mnemonic diagnoseMnemonic(String mnemonicName) {
        Mnemonic result = new Mnemonic("", "");
        result.format = MnemonicFormat.THREE;
        result.name = mnemonicName;

        for (Mnemonic mnemonic : InstructionSet.oneOperandMnemonics) {
            if (mnemonic.name.equals(mnemonicName)) {
                result.opCode = mnemonic.opCode;
                break;
            }
        }

        return result;
    }

    private static String diagnoseNIXBPE(String operand) {
        char[] nixbpe = {' ', ' ', ' ', ' ', ' ', ' '};

        // e
        nixbpe[5] = '0';
        // p
        nixbpe[4] = '1';
        // b
        nixbpe[3] = '0';
        // x
        if (operand.endsWith(",X")) nixbpe[2] = '1';
        else nixbpe[2] = '0';
        // n i
        if (!operand.startsWith("@") && !operand.startsWith("#")) {
            nixbpe[0] = '1';
            nixbpe[1] = '1';
        } else {
            if (operand.startsWith("@")) {
                nixbpe[0] = '1';
                nixbpe[1] = '0';
            } else { // startswith #
                nixbpe[0] = '0';
                nixbpe[1] = '1';

                if ((Boolean) isNumber(operand.substring(1))) {
                    nixbpe[4] = '0';
                }
            }
        }

        return String.valueOf(nixbpe);
    }

    private static boolean isNumber(String s) {

        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }

    private static String diagnoseTA(String operand) {

        if (operand.startsWith("@") || operand.startsWith("#"))
            operand = operand.substring(1);

        return getAddressFromNum_LABEL(operand);
    }

    private static String getAddressFromNum_LABEL(String operand) {

        boolean isDecimal = operand.matches("\\d*\\.?\\d+");

        if (isDecimal) {
            int decimalAddress = Integer.parseInt(operand, 10);
            return Integer.toString(decimalAddress);
        } else {
            operand = LookupTables.symbolTable.get(operand);
            int decimalAddress = Integer.parseInt(operand, 16);
            return Integer.toString(decimalAddress);
        }
    }

    private static String standardOpCode(String opCode) {
        char[] nibbles = new char[2];

        nibbles[0] = opCode.charAt(0);
        nibbles[1] = opCode.charAt(1);

        String nibbleONEBinary = Integer.toBinaryString(Integer.parseInt(String.valueOf(nibbles[0]), 16));
        String nibbleTWOBinary = Integer.toBinaryString(Integer.parseInt(String.valueOf(nibbles[1]), 16));

        StringBuilder nibbleONEBuilder = new StringBuilder(nibbleONEBinary);
        while (nibbleONEBuilder.length() < 4) nibbleONEBuilder.insert(0, "0");

        StringBuilder nibbleTWOBuilder = new StringBuilder(nibbleTWOBinary);
        while (nibbleTWOBuilder.length() < 4) nibbleTWOBuilder.insert(0, "0");

        String totalBinary = nibbleONEBuilder.toString() + nibbleTWOBuilder.toString();

        return totalBinary.substring(0, 6);
    }

    private static String standardDisp(String Disp) {
        StringBuilder stringBuilder = new StringBuilder(Disp);
        while (stringBuilder.length() < 12) stringBuilder.insert(0, "0");

        return stringBuilder.toString();
    }

    private static Instruction diagnoseJStar(Instruction instruction) {

        instruction.mnemonic.format = MnemonicFormat.THREE;
        instruction.mnemonic.opCode = "3C";
        instruction.mnemonic.name = "J";

        int disp = Integer.parseInt(instruction.memoryLocation, 16) - 3;
        String binDisp = standardDisp(Integer.toBinaryString(disp));

        // standard displacement
        StringBuilder stringBuilder = new StringBuilder(binDisp);
        while (stringBuilder.length() < 12) stringBuilder.insert(0, "0");

        String opCodeBinary = "001111" + "110010" + stringBuilder.toString();
        String opCodeHEX = Integer.toString(Integer.parseInt(opCodeBinary, 2), 16);

        // standard opCode
        StringBuilder stringBuilder1 = new StringBuilder(opCodeHEX);
        while (stringBuilder1.length() < 6) stringBuilder1.insert(0, "0");

        instruction.opCode = stringBuilder1.toString().toUpperCase();
        return instruction;
    }
}