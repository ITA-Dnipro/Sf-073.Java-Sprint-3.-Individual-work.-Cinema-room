package cinema.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReturnedTicket {

    SeatInfo returnedTicket;

    public ReturnedTicket(SeatInfo returnedTicket) {
        this.returnedTicket = returnedTicket;
    }

    @Override
    public String toString() {
        return "ReturnedTicket{" +
                "returnedTicket=" + returnedTicket +
                '}';
    }
}
