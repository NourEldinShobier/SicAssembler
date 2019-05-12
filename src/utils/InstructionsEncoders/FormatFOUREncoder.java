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
        String HEXOpCode = Integer.toString(Integer.parseInt(opCodeBinary, 2), 16);

        // Standard Opcode
        StringBuilder stringBuilder = new StringBuilder(HEXOpCode);
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

        int decimalOpCode = (Integer.parseInt(opCode, 16));

        StringBuilder stringBuilder = new StringBuilder(Integer.toBinaryString(decimalOpCode));
        while (stringBuilder.length() < 6) stringBuilder.insert(0, "0");

        return stringBuilder.toString();
    }

    private static String standardAddress(String address) {

        StringBuilder stringBuilder = new StringBuilder(address);
        while (stringBuilder.length() < 20) stringBuilder.insert(0, "0");

        return stringBuilder.toString();
    }
}
