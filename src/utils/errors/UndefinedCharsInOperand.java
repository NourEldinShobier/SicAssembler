package utils.errors;

public class UndefinedCharsInOperand extends Error {

    public UndefinedCharsInOperand() {
        super(
                "16",
                "undefined characters in operand"
        );
    }

}