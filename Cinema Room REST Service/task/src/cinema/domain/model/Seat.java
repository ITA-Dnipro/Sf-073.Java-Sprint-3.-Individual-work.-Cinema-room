package cinema.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Seat {
    @JsonProperty("row")
    private int rowPosition;
    @JsonProperty("column")
    private int columnPosition;
    @JsonProperty("price")
    private int ticketPrice;
    private boolean isAvailable;

    public Seat(int rowPosition, int columnPosition) {
        this.rowPosition = rowPosition;
        this.columnPosition = columnPosition;
        this.isAvailable = true;
    }

    public Seat() {
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isAvailable() {
        return isAvailable;
    }
}