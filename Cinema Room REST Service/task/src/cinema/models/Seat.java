package cinema.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    int row;
    int column;
    Integer sellPrice;
    String token;

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public boolean isSold() {
        return token != null;
    }
}
