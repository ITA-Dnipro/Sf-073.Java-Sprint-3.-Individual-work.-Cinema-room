package cinema.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    int row;
    int column;
    boolean isSold;
}
