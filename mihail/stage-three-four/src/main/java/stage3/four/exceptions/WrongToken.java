package stage3.four.exceptions;

import static stage3.four.exceptions.ExConstants.WRONG_TOKEN;

public class WrongToken extends RuntimeException{

    @Override
    public String getMessage() {
        return WRONG_TOKEN;
    }
}
