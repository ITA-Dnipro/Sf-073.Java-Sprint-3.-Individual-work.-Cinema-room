package antifraud.exceptionhandler;

public class ExceptionConstants {
    public static final String EXISTING_USERNAME = "The provided username already exists! " +
            "Please choose another one.";
    public static final String FAILED_AUTH = "Authentication failed at controller advice";
    public static final String USERNAME_NOT_FOUND = "The username doesn't exists!";
    public static final String VALIDATION_FAIL = "Incorrect input or missing field";

    private ExceptionConstants() {
    }
}
