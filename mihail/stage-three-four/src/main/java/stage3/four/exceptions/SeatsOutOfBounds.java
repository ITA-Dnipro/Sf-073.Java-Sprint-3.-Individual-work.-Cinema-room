package stage3.four.exceptions;

import static stage3.four.exceptions.ExConstants.SEATS_OUT_OF_BOUNDS;

public class SeatsOutOfBounds extends RuntimeException{

    @Override
    public String getMessage() {
        return SEATS_OUT_OF_BOUNDS;
    }
}
