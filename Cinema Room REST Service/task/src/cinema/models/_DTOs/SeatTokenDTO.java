package cinema.models._DTOs;

import cinema.models.Seat;
import lombok.Value;

@Value
public class SeatTokenDTO {
    String token;
    SeatPriceDTO ticket;

    public SeatTokenDTO(Seat seat) {
        this.token = seat.getToken();
        this.ticket = new SeatPriceDTO(seat.getRow(), seat.getColumn(), seat.getSellPrice());
    }
}
