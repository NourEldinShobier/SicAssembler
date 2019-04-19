package utils.Instruction;

public class Instruction
{
    public String memoryLocation = "";
    public String opCode = "";

    public Mnemonic mnemonic = new Mnemonic("","");

    public String[] segments;
    public String[] operands;

    public boolean isStartEnd = false;
    public boolean isDirective = false;


    public String standardMemoryLocationFormat(){

        StringBuilder stringBuilder = new StringBuilder(memoryLocation);

        while (stringBuilder.length() < 6) stringBuilder.insert(0, "0");

        return stringBuilder.toString();
    }
}
