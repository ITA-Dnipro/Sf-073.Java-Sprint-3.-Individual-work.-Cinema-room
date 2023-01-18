package cinema.exceprion;

public class WrongSellTokenException extends BusinessException {

    public WrongSellTokenException() {
        super("Wrong token!");
    }
}
