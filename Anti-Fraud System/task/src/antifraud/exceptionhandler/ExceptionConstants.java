package antifraud.exceptionhandler;

public class ExceptionConstants {
    public static final String EXISTING_USERNAME = "The provided username already exists!" +
            "Please choose another one.";
    public static final String FAILED_AUTH = "Authentication failed at controller advice";

    private ExceptionConstants() {
    }
}
