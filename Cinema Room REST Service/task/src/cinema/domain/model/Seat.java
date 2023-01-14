package cinema.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Seat {
    @JsonProperty("row")
    private int rowPosition;
    @JsonProperty("column")
    private int columnPosition;
    private boolean isAvailable;

    public Seat(int rowPosition, int columnPosition) {
        this.rowPosition = rowPosition;
        this.columnPosition = columnPosition;
        this.isAvailable = true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
