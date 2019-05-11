package utils.errors;

public class UndefinedCharsInOpcode extends Error {

    public UndefinedCharsInOpcode() {
        super(
                "17",
                "undefined characters in opcode"
        );
    }

}