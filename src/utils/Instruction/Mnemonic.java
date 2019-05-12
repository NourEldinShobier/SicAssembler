package utils.Instruction;

public class Mnemonic
{
    public String name;
    public String opCode;

    public MnemonicFormat format = MnemonicFormat.NA;

    public Mnemonic(String name, String opCode)
    {
        this.name = name;
        this.opCode = opCode;
    }
}
