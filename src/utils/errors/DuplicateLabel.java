package utils.errors;

public class DuplicateLabel extends Error {

    public DuplicateLabel() {
        super(
                "04",
                "duplicate label definition"
        );
    }

}