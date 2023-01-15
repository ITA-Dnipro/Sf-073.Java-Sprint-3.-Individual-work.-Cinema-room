package cinema.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter @Getter
public class Ticket {

    String token;
    SeatInfo ticket;

    public Ticket(String token, SeatInfo ticket) {
        this.token = token;
        this.ticket = ticket;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "token=" + token +
                ", ticket=" + ticket +
                '}';
    }
}
