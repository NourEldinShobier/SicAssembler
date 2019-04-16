package core;

import utils.Instruction.Mnemonic;
import utils.Instruction.MnemonicFormat;

public abstract class InstructionSet
{
    static Mnemonic[] oneOperand = {
            new Mnemonic("LDA", "00", MnemonicFormat.NA),
            new Mnemonic("LDX", "04", MnemonicFormat.NA),

            new Mnemonic("STA", "0C", MnemonicFormat.NA),
            new Mnemonic("STX", "10", MnemonicFormat.NA),

            new Mnemonic("LDCH", "50", MnemonicFormat.NA),
            new Mnemonic("STCH", "54", MnemonicFormat.NA),

            new Mnemonic("ADD", "18", MnemonicFormat.NA),
            new Mnemonic("SUB", "1C", MnemonicFormat.NA),
            new Mnemonic("MUL", "20", MnemonicFormat.NA),
            new Mnemonic("DIV", "24", MnemonicFormat.NA),

            new Mnemonic("COMP", "28", MnemonicFormat.NA),

            new Mnemonic("J", "3C", MnemonicFormat.NA),
            new Mnemonic("JLT", "38", MnemonicFormat.NA),
            new Mnemonic("JEQ", "30", MnemonicFormat.NA),
            new Mnemonic("JGT", "34", MnemonicFormat.NA),

            new Mnemonic("JSUB", "48", MnemonicFormat.NA),
            new Mnemonic("RSUB", "4C", MnemonicFormat.NA),

            new Mnemonic("TD", "E0", MnemonicFormat.NA),
            new Mnemonic("WD", "DC", MnemonicFormat.NA),
            new Mnemonic("RD", "D8", MnemonicFormat.NA),
    };

    static Mnemonic[] twoOperands = {
            new Mnemonic("RMO", "AC", MnemonicFormat.TWO),
    };

    static Mnemonic[] directives = {
            new Mnemonic("RESW", "", MnemonicFormat.NA),
            new Mnemonic("RESB", "", MnemonicFormat.NA),
            new Mnemonic("WORD", "", MnemonicFormat.NA),
            new Mnemonic("BYTE", "", MnemonicFormat.NA),
    };

    static Mnemonic[] BeginEnd = {
            new Mnemonic("START", "", MnemonicFormat.NA),
            new Mnemonic("END", "", MnemonicFormat.NA),
    };
}
