package stage3.four.exceptions;

import static stage3.four.exceptions.ExConstants.TICKET_ALREADY_PURCHASED;

public class TicketAlreadyPurchased extends RuntimeException{

    @Override
    public String getMessage() {
        return TICKET_ALREADY_PURCHASED;
    }
}
