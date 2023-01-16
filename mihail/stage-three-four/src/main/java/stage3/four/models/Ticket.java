package stage3.four.models;

import java.util.UUID;

public class Ticket {

    private UUID token;
    private int price;
    private Seat seat;

    public Ticket(UUID token, Seat seat) {
        setToken(token);
        setSeat(seat);
        setPrice();
    }

    public Seat getSeat() {
        return seat;
    }

    private void setSeat(Seat seat) {
        this.seat = seat;
    }

    public int getPrice() {
        return price;
    }

    private void setPrice() {
        price = seat.getRow() <= 4 ? 10 : 8;
    }

    public UUID getToken() {
        return token;
    }

    private void setToken(UUID token) {
        this.token = token;
    }
}
