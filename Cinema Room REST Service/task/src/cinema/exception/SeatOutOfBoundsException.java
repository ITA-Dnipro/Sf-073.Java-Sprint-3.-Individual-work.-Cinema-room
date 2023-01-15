package cinema.exception;

import cinema.domain.model.Seat;

public class SeatOutOfBoundsException extends RuntimeException{
    public SeatOutOfBoundsException(Seat seat) {
        super("The number of a row or a column is out of bounds!");
    }
}
