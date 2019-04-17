package utils.Instruction;

public class Mnemonic
{
    public String name;
    public String opCode;
    public String nixbpe;

    public MnemonicFormat format;

    public Mnemonic(String name, String opCode)
    {
        this.name = name;
        this.opCode = opCode;
    }
}
