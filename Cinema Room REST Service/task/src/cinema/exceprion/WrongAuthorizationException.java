package cinema.exceprion;

public class WrongAuthorizationException extends BusinessException {
    public WrongAuthorizationException() {
        super("The password is wrong!");
    }
}
