package exceptions;

public class InvalidDateFormatException extends Exception {
    public InvalidDateFormatException() {
        super("invalid date format");
    }
}