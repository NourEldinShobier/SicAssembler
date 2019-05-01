package utils.errors;

public class StatementMustHaveLabel extends Error {

    public StatementMustHaveLabel() {
        super(
                "14",
                "this statement must have a label"
        );
    }

}
