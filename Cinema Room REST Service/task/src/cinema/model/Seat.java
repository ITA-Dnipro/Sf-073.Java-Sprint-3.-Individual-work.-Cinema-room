package cinema.model;

import lombok.Getter;

@Getter
public class Seat {
    public int row;
    public int column;

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
    }
}
