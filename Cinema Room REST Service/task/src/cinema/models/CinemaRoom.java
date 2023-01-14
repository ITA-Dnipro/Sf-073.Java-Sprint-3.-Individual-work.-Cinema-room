package cinema.models;

import lombok.Getter;


import java.util.ArrayList;
import java.util.List;
@Getter
public class CinemaRoom {
    private int total_rows = 9;
    private int total_columns = 9;
    private List<Seat> available_seats;

    public CinemaRoom() {
        this.available_seats = new ArrayList<>();
        for (int i = 1; i <= total_rows; i++) {
            for (int j = 1; j <= total_columns; j++) {
                available_seats.add(new Seat(i, j));
            }
        }
    }
}
