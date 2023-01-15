package cinema.exception;

public class AlreadySoldException extends RuntimeException{
    public AlreadySoldException(){
        super("The ticket has been already purchased!");
    }
}
