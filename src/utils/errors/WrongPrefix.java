package utils.errors;

public class WrongPrefix extends Error {

    public WrongPrefix() {
        super(
                "07",
                "wrong g operation prefix"
        );
    }

}