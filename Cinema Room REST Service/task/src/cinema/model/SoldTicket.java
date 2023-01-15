package cinema.model;

public class SoldTicket {
    String token;
    SeatInfo ticket;

    public SoldTicket(Seat seat) {
        this.token = seat.getToken();
        this.ticket = new SeatInfo(seat.getRow(), seat.getColumn(), seat.getPrice());
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public SeatInfo getTicket() {
        return ticket;
    }

    public void setTicket(SeatInfo ticket) {
        this.ticket = ticket;
    }
}
