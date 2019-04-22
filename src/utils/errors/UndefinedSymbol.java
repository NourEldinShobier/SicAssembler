package utils.errors;

public class UndefinedSymbol extends Error {

    public UndefinedSymbol() {
        super(
                "09",
                "undefined symbol in operand"
        );
    }

}