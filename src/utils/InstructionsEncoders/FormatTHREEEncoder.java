package utils.InstructionsEncoders;

import core.InstructionSet;
import core.Literal;
import core.LookupTables;
import utils.ExpressionEvaluator;
import utils.Instruction.Instruction;
import utils.Instruction.Mnemonic;
import utils.Instruction.MnemonicFormat;
import utils.InstructionIdentifier;

public class FormatTHREEEncoder {
    public static Instruction encode(Instruction instruction) {
        if (instruction.segments[1].equals("J") && instruction.segments[2].equals("*"))
            return diagnoseJStar(instruction);

        instruction.mnemonic = diagnoseMnemonic(instruction.segments[1]);

        String nixbpe = diagnoseNIXBPE(instruction.segments[2]);
        String disp = "";

        if (instruction.segments[2].startsWith("=")){
            Literal literal = new Literal(instruction.segments[2], instruction.memoryLocation);
            Literal.literalToHex(instruction.segments[2]);
            String add = Literal.getHexValue();
            int addInt = Integer.parseInt(add,16);
            add = Integer.toBinaryString(addInt);
            disp = add;
            InstructionIdentifier.diagnoseLiteral(literal);
        }else {
            if ((nixbpe.charAt(0) == '0' && nixbpe.charAt(1) == '1' && nixbpe.charAt(4) == '0')
                    || (nixbpe.charAt(0) == '1' && nixbpe.charAt(1) == '0' && nixbpe.charAt(4) == '0')) {

                String operand = instruction.segments[2].substring(1);
                boolean isDecimal = operand.matches("\\d*\\.?\\d+");

                if (isDecimal) disp = Integer.toBinaryString(Integer.parseInt(operand));
                else {
                    operand = LookupTables.symbolTable.get(operand);

                    int decimalAddress = Integer.parseInt(operand, 16);
                    disp = Integer.toBinaryString(decimalAddress);
                }
            } else if (nixbpe.charAt(0) == '1' && nixbpe.charAt(1) == '1' && nixbpe.charAt(4) == '0') {
                String operand = instruction.segments[2];
                boolean isDecimal = operand.matches("\\d*\\.?\\d+");

                if (isDecimal) disp = Integer.toBinaryString(Integer.parseInt(operand));
                else {
                    operand = LookupTables.symbolTable.get(operand);

                    int decimalAddress = Integer.parseInt(operand, 16);
                    disp = Integer.toBinaryString(decimalAddress);
                }
            }
        }

        String opCodeBinary = standardOpCode(instruction.mnemonic.opCode) + nixbpe + standardDisplacement(disp);
        String opCodeHEX = Integer.toString(Integer.parseInt(opCodeBinary, 2), 16);

        // Standard Opcode
        StringBuilder stringBuilder = new StringBuilder(opCodeHEX);
        while (stringBuilder.length() < 6) stringBuilder.insert(0, "0");

        instruction.opCode = stringBuilder.toString().toUpperCase();
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

        if (operand.startsWith("=")) {
            nixbpe[0] = '0';
            nixbpe[1] = '1';
            nixbpe[2] = '0';
            nixbpe[3] = '0';
            nixbpe[4] = '0';
            nixbpe[5] = '0';
        } else {
            // e
            nixbpe[5] = '0';
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

            // p
            boolean startWithHashORat = operand.startsWith("#") || operand.startsWith("@");

            if (startWithHashORat) operand = operand.substring(1);
            boolean isDecimal = operand.matches("\\d*\\.?\\d+");

            if (isDecimal && startWithHashORat) nixbpe[4] = '0';
            else if (isDecimal && !startWithHashORat) nixbpe[4] = '0';
            else if (!isDecimal && startWithHashORat) nixbpe[4] = '0';
            else {
                nixbpe[4] = '0';
            }
        }




        return String.valueOf(nixbpe);
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

    private static String standardDisplacement(String disp) {
        StringBuilder stringBuilder = new StringBuilder(disp);
        while (stringBuilder.length() < 12) stringBuilder.insert(0, "0");

        return stringBuilder.toString();
    }

    private static Instruction diagnoseJStar(Instruction instruction) {

        instruction.mnemonic.format = MnemonicFormat.THREE;
        instruction.mnemonic.opCode = "3C";
        instruction.mnemonic.name = "J";

        String memLocationHex = instruction.memoryLocation;
        String memLocationBin = Integer.toBinaryString(Integer.parseInt(memLocationHex, 16));

        if (memLocationBin.length() > 12) instruction.opCode = "3F2FFD";
        else {

            String first3Digits = "3F0";
            String second3Digits = instruction.memoryLocation.toUpperCase();

            StringBuilder stringBuilder = new StringBuilder(second3Digits);
            while (stringBuilder.length() < 3) stringBuilder.insert(0, "0");

            instruction.opCode = first3Digits + stringBuilder.toString();
        }

        return instruction;
    }
}