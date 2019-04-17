package utils.Instruction;

public class Instruction
{
    public String memoryLocation;
    public String opCode;

    public Mnemonic mnemonic;

    public String[] segments;
    public String[] operands;

    public boolean isStartEnd = false;
    public boolean isDirective = false;
}
