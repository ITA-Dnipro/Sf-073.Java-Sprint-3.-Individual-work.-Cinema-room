package antifraud.exceptionhandler;

public class ExceptionConstants {
    public static final String EXISTING_USERNAME = "The provided username already exists! " +
            "Please choose another one.";
    public static final String EXISTING_IP = "The provided IP already exists in the database!";
    public static final String FAILED_AUTH = "Authentication failed at controller advice";
    public static final String USERNAME_NOT_FOUND = " - this user is not found!";
    public static final String VALIDATION_FAIL = "Incorrect input or missing field";
    public static final String ADMIN = "There is already existent admin!";
    public static final String SAME_ROLE = "This role has already been provided!";
    public static final String CANNOT_BE_BLOCKED = "This role cannot be blocked!";
    public static final String ROLE_NON_EXIST = "The role doesn't exists!";

    private ExceptionConstants() {
    }
}