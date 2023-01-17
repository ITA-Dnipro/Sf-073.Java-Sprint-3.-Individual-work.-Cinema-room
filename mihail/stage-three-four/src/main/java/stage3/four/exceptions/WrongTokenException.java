package stage3.four.exceptions;

import static stage3.four.exceptions.ExConstants.WRONG_TOKEN;

public class WrongTokenException extends RuntimeException{

    @Override
    public String getMessage() {
        return WRONG_TOKEN;
    }
}
