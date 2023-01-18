package cinema.model.response;

import cinema.model.Seat;
import lombok.Value;

@Value
public class TicketResponse {
    String token;
    Seat ticket;

    public TicketResponse(Seat ticket) {
        this.ticket = ticket;
        this.token = ticket.getSellToken();
    }
}
