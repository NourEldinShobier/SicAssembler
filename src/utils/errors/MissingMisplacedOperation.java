package utils.errors;

public class MissingMisplacedOperation extends Error {

    public MissingMisplacedOperation() {
        super(
                "02",
                "missing or misplaced operation mnemonic"
        );
    }

}