package core;

import utils.Instruction.Mnemonic;
import utils.Instruction.MnemonicFormat;

public abstract class InstructionSet
{
    static Mnemonic noOperands[] = {
            new Mnemonic("FIX", "", MnemonicFormat.ONE),
    };

    static Mnemonic oneOperand[] = {
            new Mnemonic("ADD", "", MnemonicFormat.NA),
            new Mnemonic("SUB", "", MnemonicFormat.NA),
    };

    static Mnemonic twoOperands[] = {
            new Mnemonic("RMO", "", MnemonicFormat.NA),
    };

    static Mnemonic directives[] = {
            new Mnemonic("RESW", "", MnemonicFormat.NA),
            new Mnemonic("RESB", "", MnemonicFormat.NA),
            new Mnemonic("WORD", "", MnemonicFormat.NA),
            new Mnemonic("BYTE", "", MnemonicFormat.NA),
    };

    static Mnemonic BeginEnd[] = {
            new Mnemonic("START", "", MnemonicFormat.NA),
            new Mnemonic("END", "", MnemonicFormat.NA),
    };
}
