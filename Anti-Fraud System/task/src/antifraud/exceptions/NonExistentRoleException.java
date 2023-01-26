package antifraud.exceptions;

public class NonExistentRoleException extends BusinessException {
    public NonExistentRoleException() {
    }

    public NonExistentRoleException(String message) {
        super(message);
    }
}
