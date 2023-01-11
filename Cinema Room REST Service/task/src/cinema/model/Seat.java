package cinema.model;

import lombok.Data;

import java.util.Objects;

@Data
public class Seat {
    public int row;
    public int column;
    public int price;

    public Seat() {

    }

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        this.price = calculatePrice();
    }

    public int calculatePrice() {
        return row < 4? 10:8;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seat)) return false;
        Seat seat = (Seat) o;
        return getRow() == seat.getRow() && getColumn() == seat.getColumn();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRow(), getColumn());
    }
}

