package cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Value;

import java.util.Objects;

@Data
public class Seat {
    public int row;
    public int column;
    public int price;
    @JsonIgnore
    private String sellToken;

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
    }


    @JsonIgnore
    public boolean isSold() {
        return sellToken != null;
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

