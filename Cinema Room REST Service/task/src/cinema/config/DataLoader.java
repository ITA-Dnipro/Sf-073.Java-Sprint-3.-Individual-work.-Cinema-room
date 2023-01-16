package cinema.config;

import cinema.domain.model.Seat;
import cinema.persistence.repository.SeatRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    private final SeatRepository seatRepository;
    private final CinemaProperties cinemaProperties;

    public DataLoader(SeatRepository seatRepository, CinemaProperties cinemaProperties) {
        this.seatRepository = seatRepository;
        this.cinemaProperties = cinemaProperties;
    }

    @Override
    public void run(String... args) {
        List<Seat> seats = new ArrayList<>();
        for (int row = 1; row <= cinemaProperties.getTotalRows(); row++) {
            for (int column = 1; column <= cinemaProperties.getTotalColumns(); column++) {
                seats.add(new Seat(row, column));
            }
        }
        seatRepository.saveCinemaRoom(seats);
    }

}