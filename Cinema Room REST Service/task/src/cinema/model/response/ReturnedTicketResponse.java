package cinema.model.response;

import cinema.model.Seat;
import lombok.Value;

@Value
public class ReturnedTicketResponse {
    Seat returned_ticket;

    public ReturnedTicketResponse(Seat returned_ticket) {
        this.returned_ticket = returned_ticket;
    }
}
