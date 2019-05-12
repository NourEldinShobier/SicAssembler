package utils.InstructionsEncoders;

import core.InstructionSet;
import core.LookupTables;
import utils.Instruction.Instruction;
import utils.Instruction.Mnemonic;
import utils.Instruction.MnemonicFormat;

import java.util.Arrays;

public class FormatFOUREncoder {
    public static Instruction encode(Instruction instruction) {
        instruction.mnemonic = diagnoseMnemonic(instruction.segments[1]);

        String nixbpe = diagnoseNIXBPE(instruction.segments[2]);
        String address = diagnoseAddress(instruction.segments[2]);

        // convert nixbpe to HEX
        int decimal = Integer.parseInt(nixbpe,2);
        nixbpe = Integer.toString(decimal,16);

        instruction.opCode = instruction.mnemonic.opCode + " " + nixbpe + " " + address;

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
        char[] nixbpe = {' ',' ',' ',' ',' ',' '};

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

        if (!operand.startsWith("@") && !operand.startsWith("#")) {
            boolean isHex = operand.matches("^[0-9a-fA-F]+$");

            if (isHex) return operand;
            else return LookupTables.symbolTable.get(operand);
        } else {
            operand = operand.substring(1);

            boolean isHex = operand.matches("^[0-9a-fA-F]+$");

            if (isHex) return operand;
            else return LookupTables.symbolTable.get(operand);
        }
    }
}
