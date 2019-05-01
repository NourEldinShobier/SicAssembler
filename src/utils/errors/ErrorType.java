package utils.errors;

public class ErrorType {
    public static Error MisplacedLabel = new MisplacedLabel();
    public static Error MissingMisplacedOperation = new MissingMisplacedOperation();
    public static Error MissingMisplacedOperand = new MissingMisplacedOperand();
    public static Error DuplicateLabel = new DuplicateLabel();
    public static Error StatementCannotHaveLabel = new StatementCannotHaveLabel();
    public static Error StatementCannotHaveOperand = new StatementCannotHaveOperand();
    public static Error WrongPrefix = new WrongPrefix();
    public static Error UnrecognizedOperation = new UnrecognizedOperation();
    public static Error UndefinedSymbol = new UndefinedSymbol();
    public static Error NotHexaString = new NotHexaString();
    public static Error CannotBeFormatFour = new CannotBeFormatFour();
    public static Error IllegalRegisterAddress = new IllegalRegisterAddress();
    public static Error MissingEndStatement = new MissingEndStatement();

    public static Error StatementMustHaveLabel = new StatementMustHaveLabel();
    public static Error NotDecimalString = new NotDecimalString();
}
