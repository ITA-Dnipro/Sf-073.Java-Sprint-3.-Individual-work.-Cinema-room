package cinema.exceprion;

public class TicketAlreadySoldException extends BusinessException {

    public TicketAlreadySoldException(String message) {
        super(message);
    }
}