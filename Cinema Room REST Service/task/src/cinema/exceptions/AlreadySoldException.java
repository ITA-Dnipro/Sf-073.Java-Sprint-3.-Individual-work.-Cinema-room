package cinema.exceptions;

public class AlreadySoldException extends BusinessException {
    public AlreadySoldException() {
        super("The ticket has been already purchased!");
    }
}
