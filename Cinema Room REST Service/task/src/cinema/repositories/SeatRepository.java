package cinema.repositories;

import cinema.config.CinemaProperties;
import cinema.models.Seat;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
                seats.add(new Seat(i, j));
            }
        }
    }

    public List<Seat> getAvailableSeats() {
        return seats;
    }
}
