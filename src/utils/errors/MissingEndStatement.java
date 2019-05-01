package utils.errors;

public class MissingEndStatement extends Error {

    public MissingEndStatement() {
        super(
                "13",
                "missing END statement"
        );
    }

}
