package stage3.four.exceptions;

import static stage3.four.exceptions.ExConstants.WRONG_OR_MISSING_PASSWORD;
import static stage3.four.exceptions.ExConstants.WRONG_TOKEN;

public class MissingOrWrongPasswordException extends RuntimeException{

    @Override
    public String getMessage() {
        return WRONG_OR_MISSING_PASSWORD;
    }
}
