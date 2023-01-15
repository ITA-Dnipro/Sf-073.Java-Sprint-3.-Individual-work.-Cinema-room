package cinema.model;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Seat {

    public int row;
    public int column;
    public boolean isBooked;


    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        this.isBooked = false;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "row=" + row +
                ", column=" + column +
                ", isBooked=" + isBooked +
                '}';
    }
}
