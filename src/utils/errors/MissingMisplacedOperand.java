package utils.errors;

public class MissingMisplacedOperand extends Error {

    public MissingMisplacedOperand() {
        super(
                "03",
                "missing or misplaced operand field"
        );
    }

}