package utils.errors;

public class StatementCannotHaveOperand extends Error {

    public  StatementCannotHaveOperand() {
        super(
                "06",
                "this statement can’t have an operand"
        );
    }

}