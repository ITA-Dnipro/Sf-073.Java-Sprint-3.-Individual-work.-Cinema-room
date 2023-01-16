package stage3.four.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Room {

    @SerializedName("total_rows")
    private final int totalRows;
    @SerializedName("total_columns")
    private final int totalColumns;
    @SerializedName("available_seats")
    private List<Seat> availableSeats;

    public Room(int totalRows, int totalColumns) {
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
        this.availableSeats = new ArrayList<>();
        setAvailableSeats(availableSeats);
    }

    public List<Seat> getAvailableSeats() {
        return availableSeats;
    }

    private void setAvailableSeats(List<Seat> availableSeats) {
        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < totalColumns; j++) {
                this.availableSeats.add(new Seat(i + 1, j + 1));
            }
        }
        this.availableSeats = availableSeats;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }
}
