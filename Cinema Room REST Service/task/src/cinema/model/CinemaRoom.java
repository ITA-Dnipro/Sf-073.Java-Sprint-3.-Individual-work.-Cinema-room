package cinema.model;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class CinemaRoom {
    @org.springframework.beans.factory.annotation.Value("${cinema.total-rows:9}")
    public int total_rows;
    @org.springframework.beans.factory.annotation.Value("${cinema.total-columns:9}")
    public int total_columns;
    public List<Seat> available_seats = new ArrayList<>();
    private final List<Seat> purchased_seats = new ArrayList<>();

    @PostConstruct
    public void init() {
        for (int i = 1; i <= total_rows; i++) {
            for (int j = 1; j <= total_columns; j++) {
                available_seats.add(new Seat(i, j));
            }
        }
    }

    public void purchaseSeat(Seat seat){
        if (seatAreInAvailableSeats(seat) && !seatWasPurchased(seat)) {
            purchased_seats.add(seat);
            available_seats.remove(seat);
        }
    }

    public boolean seatAreInAvailableSeats(Seat seat){
        return available_seats!=null && available_seats.contains(seat);
    }

    public boolean seatWasPurchased(Seat seat){
        return purchased_seats.contains(seat);
    }
}
