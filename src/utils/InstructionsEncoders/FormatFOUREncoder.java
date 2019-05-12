package utils.InstructionsEncoders;

import core.InstructionSet;
import core.LookupTables;
import utils.Instruction.Instruction;
import utils.Instruction.Mnemonic;
import utils.Instruction.MnemonicFormat;

public class FormatFOUREncoder {
    public static Instruction encode(Instruction instruction) {
        instruction.mnemonic = diagnoseMnemonic(instruction.segments[1]);

        String nixbpe = diagnoseNIXBPE(instruction.segments[2]);
        String address = diagnoseAddress(instruction.segments[2]);

        String opCodeBinary = standardOpCode(instruction.mnemonic.opCode) + nixbpe + standardAddress(address);
        String opCodeHEX = Integer.toString(Integer.parseInt(opCodeBinary, 2), 16);

        // Standard Opcode
        StringBuilder stringBuilder = new StringBuilder(opCodeHEX);
        while (stringBuilder.length() < 8) stringBuilder.insert(0, "0");

        instruction.opCode = stringBuilder.toString().toUpperCase();
        return instruction;
    }

    private static Mnemonic diagnoseMnemonic(String mnemonicName) {
        Mnemonic result = new Mnemonic("", "");
        result.format = MnemonicFormat.FOUR;
        result.name = mnemonicName;

        mnemonicName = mnemonicName.substring(1);

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
        nixbpe[5] = '1';
        // p
        nixbpe[4] = '0';
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
            } else {
                nixbpe[0] = '0';
                nixbpe[1] = '1';
            }
        }

        return String.valueOf(nixbpe);
    }

    private static String diagnoseAddress(String operand) {

        if (operand.startsWith("@") || operand.startsWith("#"))
            operand = operand.substring(1);

        return getBinAddressFromNum_LABEL(operand);
    }

    private static String getBinAddressFromNum_LABEL(String operand) {
        boolean isDecimal = operand.matches("\\d*\\.?\\d+");

        if (isDecimal) {
            int decimalAddress = Integer.parseInt(operand, 10);
            return Integer.toBinaryString(decimalAddress);
        } else {
            operand = LookupTables.symbolTable.get(operand);

            int decimalAddress = Integer.parseInt(operand, 16);
            return Integer.toBinaryString(decimalAddress);
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

        return totalBinary.substring(0,6);
    }

    private static String standardAddress(String address) {
        StringBuilder stringBuilder = new StringBuilder(address);
        while (stringBuilder.length() < 20) stringBuilder.insert(0, "0");

        return stringBuilder.toString();
    }
}
