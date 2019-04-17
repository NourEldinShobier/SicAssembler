package core;

import utils.Instruction.Mnemonic;

public abstract class InstructionSet
{
    static Mnemonic[] noOperands = {
            new Mnemonic("RMO", "AC"),
    };

    static Mnemonic[] oneOperand = {
            new Mnemonic("LDA", "00"),

    };

    static Mnemonic[] twoOperands = {
            new Mnemonic("RMO", "AC"),
    };
}
