package utils.errors;

public class UnrecognizedOperation extends Error {

    public UnrecognizedOperation() {
        super(
                "08",
                "unrecognized operation code"
        );
    }

}