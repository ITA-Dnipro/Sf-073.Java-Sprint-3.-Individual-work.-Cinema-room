package cinema.exeption;

public class WrongTokenException extends BusinessException {
    public WrongTokenException() {
        super("Wrong token!");
    }
}
