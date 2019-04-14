package utils.Instruction;

public class Mnemonic
{
    String name;
    String opCode;

    MnemonicFormat format;

    public Mnemonic(String name, String opCode, MnemonicFormat format)
    {
        this.name = name;
        this.opCode = opCode;
        this.format = format;
    }
}
