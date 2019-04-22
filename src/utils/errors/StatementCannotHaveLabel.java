package utils.errors;

public class StatementCannotHaveLabel extends Error {

    public StatementCannotHaveLabel() {
        super(
                "05",
                "this statement can’t have a label"
        );
    }

}
