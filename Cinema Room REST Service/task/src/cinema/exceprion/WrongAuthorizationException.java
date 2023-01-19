package cinema.exceprion;

public class WrongAuthorizationException extends BusinessException {
    public WrongAuthorizationException(String message) {
        super(message);
    }
}
