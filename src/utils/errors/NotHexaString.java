package utils.errors;

public class NotHexaString extends Error {

    public  NotHexaString() {
        super(
                "10",
                "not a hexadecimal string"
        );
    }

}