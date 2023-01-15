package cinema.exception;

public class WrongTokenException extends RuntimeException{
    public WrongTokenException(){
        super("Wrong token!");
    }
}
