package utils.errors;

public class IllegalRegisterAddress extends Error {

    public IllegalRegisterAddress() {
        super(
                "12",
                "illegal address for a register"
        );
    }

}
