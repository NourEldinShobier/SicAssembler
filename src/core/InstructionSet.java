package core;

import utils.Instruction.Mnemonic;

public abstract class InstructionSet
{
    public static Mnemonic[] oneOperandMnemonics = {
            new Mnemonic("LDA", "00"),
            new Mnemonic("LDB", "68"),
            new Mnemonic("LDF", "70"),
            new Mnemonic("LDL", "08"),
            new Mnemonic("LDS", "6C"),
            new Mnemonic("LDT", "74"),
            new Mnemonic("LDX", "04"),
            new Mnemonic("LDCH", "50"),

            new Mnemonic("STA", "0C"),
            new Mnemonic("STB", "78"),
            new Mnemonic("STCH", "54"),
            new Mnemonic("STF", "80"),
            new Mnemonic("STL", "14"),
            new Mnemonic("STS", "7C"),
            new Mnemonic("STT", "84"),
            new Mnemonic("STX", "10"),

            new Mnemonic("ADD", "18"),
            new Mnemonic("SUB", "1C"),
            new Mnemonic("COMP", "28"),

            new Mnemonic("J", "3C"),
            new Mnemonic("JEQ", "30"),
            new Mnemonic("JLT", "38"),
            new Mnemonic("JGT", "34"),
            new Mnemonic("TIX", "2C"),

    };

    public static Mnemonic[] twoOperandsMnemonics = {
            new Mnemonic("RMO", "AC"),
            new Mnemonic("TIXR", "B8"),
            new Mnemonic("ADDR", "90"),
            new Mnemonic("SUBR", "94"),
            new Mnemonic("COMPR", "A0"),
    };
}
