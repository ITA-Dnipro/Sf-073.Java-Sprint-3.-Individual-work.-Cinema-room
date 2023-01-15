package cinema.repository;

import cinema.model.Seat;
import cinema.model.SeatInfo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SeatRepository {

    int totalRows = 9;
    int totalColumns = 9;
    int frontRows = 4;
    int frontRowsPrice = 10;
    int backRowsPrice = 8;
    List<Seat> seats = new ArrayList<>();


    public SeatRepository() {
        for (int i = 1; i <= totalRows; i++) {
            for (int j = 1; j <= totalColumns; j++) {
                this.seats.add(new Seat(i, j));
            }
        }
    }

    public List<Seat> getAvailableSeats() {
        return seats.stream().filter(s -> !s.isBooked()).toList();
    }


    public void markSeatAsBooked(Seat seat) {
        seats.stream().filter(s -> s.getRow() == seat.getRow() && s.getColumn() == seat.getColumn()).findFirst().ifPresent(s -> s.setBooked(true));
    }

    public boolean isBooked(Seat seat) {
        return seats.stream()
                .filter(s -> s.getRow() == seat.getRow() && s.getColumn() == seat.getColumn())
                .filter(s -> s.isBooked)
                .findFirst()
                .isPresent();
    }

    public boolean isSeatPresent(Seat seat) {
        return seat.getRow() > 0 && seat.getRow() < 10 && seat.getColumn() > 0 && seat.getColumn() < 10;
    }
}
