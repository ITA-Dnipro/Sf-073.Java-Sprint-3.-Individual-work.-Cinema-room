package cinema.repositories;

import cinema.config.CinemaProperties;
import cinema.models.DTOs.SeatCoordinates;
import cinema.models.Seat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class SeatRepository {

    final private CinemaProperties properties;
    private List<Seat> seats;


    @PostConstruct
    public void init() {
        this.seats = new ArrayList<>();
        for (int i = 1; i <= properties.getTotalRows(); i++) {
            for (int j = 1; j <= properties.getTotalColumns(); j++) {
                seats.add(new Seat(i, j, false));
            }
        }
    }

    public List<SeatCoordinates> getAvailableSeats() {
        return seats
                .stream()
                .filter(seat -> !seat.isSold())
                .map(SeatCoordinates::new)
                .toList();
    }

    public boolean isAvailable(SeatCoordinates seat) {
        return seats.stream()
                .filter(s -> s.getRow() == seat.getRow() &&
                        s.getColumn() == seat.getColumn())
                .filter(s -> !s.isSold())
                .findFirst().isPresent();
    }

    public void markAsSold(SeatCoordinates seat) {
        seats.stream()
                .filter(s -> s.getRow() == seat.getRow() &&
                        s.getColumn() == seat.getColumn())
                .findFirst()
                .ifPresent(s -> s.setSold(true));
    }
}
