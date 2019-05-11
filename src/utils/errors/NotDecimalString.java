package utils.errors;

public class NotDecimalString extends Error {

    public NotDecimalString() {
        super(
                "15",
                "operand is not a decimal string"
        );
    }

}