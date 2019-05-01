package utils.errors;

public class NotDecimalString extends Error {

    public NotDecimalString() {
        super(
                "15",
                "not a decimal string"
        );
    }

}