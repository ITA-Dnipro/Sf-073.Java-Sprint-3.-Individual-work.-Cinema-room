package cinema.rest.dto;

import cinema.domain.model.Seat;
import com.fasterxml.jackson.annotation.JsonProperty;

public record SeatDTO(@JsonProperty("row")
                      int rowPosition,
                      @JsonProperty("column")
                      int columnPosition,
                      @JsonProperty("price")
                      int ticketPrice) {


    public static SeatDTO fromModel(Seat seat) {
        return new SeatDTO(seat.getRowPosition(),
                seat.getColumnPosition(),
                seat.getTicketPrice());
    }

    public Seat toModel() {
        return new Seat(rowPosition, columnPosition);
    }
}
