package utils.InstructionsEncoders;

import core.InstructionSet;
import core.Registers;
import utils.Instruction.Instruction;
import utils.Instruction.Mnemonic;
import utils.Instruction.MnemonicFormat;

public class FormatTWOEncoder {
    public static Instruction encode(Instruction instruction){
        instruction.mnemonic = diagnoseMnemonic(instruction.segments[1]);

        String registers = diagnoseRegisters(instruction.segments[2]);
        String binOpCode = standardOpCode(instruction.mnemonic.opCode) + registers;
        String HEXOpCode = Integer.toString(Integer.parseInt(binOpCode, 2), 16);

        // Standard Opcode
        StringBuilder stringBuilder = new StringBuilder(HEXOpCode);
        while (stringBuilder.length() < 4) stringBuilder.insert(0, "0");

        instruction.opCode = stringBuilder.toString().toUpperCase();

        System.out.println(instruction.opCode);

        return instruction;
    }

    private static Mnemonic diagnoseMnemonic(String mnemonicName) {
        Mnemonic result = new Mnemonic("", "");
        result.format = MnemonicFormat.TWO;
        result.name = mnemonicName;

        for (Mnemonic mnemonic : InstructionSet.twoOperandsMnemonics) {
            if (mnemonic.name.equals(mnemonicName)) {
                result.opCode = mnemonic.opCode;
                break;
            }
        }

        return result;
    }

    private static String diagnoseRegisters(String operand){
        String[] registers = operand.split(",");

        registers[0] = Registers.get(registers[0]);
        registers[1] = Registers.get(registers[1]);

        return registers[0] + registers[1];
    }

    private static String standardOpCode(String opCode) {

        int decimalOpCode = (Integer.parseInt(opCode, 16));

        StringBuilder stringBuilder = new StringBuilder(Integer.toBinaryString(decimalOpCode));
        while (stringBuilder.length() < 8) stringBuilder.insert(0, "0");

        return stringBuilder.toString();
    }
}
