package cinema.exceptions;

import lombok.Value;


public class WrongTokenException extends BusinessException {
    public WrongTokenException() {
        super("Wrong token!");
    }
}
