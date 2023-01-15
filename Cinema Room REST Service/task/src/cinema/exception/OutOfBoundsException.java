package cinema.exception;

public class OutOfBoundsException extends RuntimeException{
    public OutOfBoundsException(){
        super("The number of a row or a column is out of bounds!");
    }
}
