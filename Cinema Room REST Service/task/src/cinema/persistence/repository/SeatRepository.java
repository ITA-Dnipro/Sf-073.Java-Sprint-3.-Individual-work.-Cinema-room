package cinema.persistence.repository;

import cinema.config.CinemaProperties;
import cinema.domain.model.CinemaRoom;
import cinema.domain.model.Seat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class SeatRepository {
    private final CinemaProperties cinemaProperties;
    private List<Seat> seats;

    public SeatRepository(CinemaProperties cinemaProperties) {
        this.cinemaProperties = cinemaProperties;
    }

    @PostConstruct
    void init() {
        seats = new ArrayList<>();
        for (int row = 1; row <= cinemaProperties.getTotalRows(); row++) {
            for (int column = 1; column <= cinemaProperties.getTotalColumns(); column++) {
                Seat seat = new Seat(row, column);
                if (row <= cinemaProperties.getFrontRows()) {
                    seat.setTicketPrice(cinemaProperties.getTicketPriceFront());
                } else {
                    seat.setTicketPrice(cinemaProperties.getTicketPriceBack());
                }
                seats.add(seat);
            }
        }
    }

    public CinemaRoom getCinemaRoom() {
        return new CinemaRoom(cinemaProperties.getTotalRows(),
                cinemaProperties.getTotalColumns(),
                findAllAvailableSeats());
    }

    private List<Seat> findAllAvailableSeats() {
        return seats.stream()
                .filter(Seat::isAvailable)
                .toList();
    }

    public Optional<Seat> save(Seat seat) {
        boolean seatAvailable = isSeatAvailable(seat);
        return seatAvailable ? Optional.of(seatWithTicketPrice(seat)) : Optional.empty();
    }

    private boolean isSeatAvailable(Seat seat) {
        boolean availability = seats.stream()
                .filter(s -> s.getRowPosition() == seat.getRowPosition() &&
                        s.getColumnPosition() == seat.getColumnPosition())
                .anyMatch(Seat::isAvailable);

        if (availability) {
            seats.stream()
                    .filter(s -> s.getRowPosition() == seat.getRowPosition() &&
                            s.getColumnPosition() == seat.getColumnPosition())
                    .findFirst()
                    .ifPresent(s -> s.setAvailable(false));
            seat.setAvailable(false);
        }
        return availability;
    }

    private Seat seatWithTicketPrice(Seat seat) {
        if (seat.getRowPosition() <= cinemaProperties.getFrontRows()) {
            seat.setTicketPrice(cinemaProperties.getTicketPriceFront());
        } else {
            seat.setTicketPrice(cinemaProperties.getTicketPriceBack());
        }
        return seat;
    }

    public boolean isSeatPresent(Seat seat) {
        return seat.getRowPosition() > 0 &&
                seat.getRowPosition() <= cinemaProperties.getTotalRows() &&
                seat.getColumnPosition() > 0 &&
                seat.getColumnPosition() <= cinemaProperties.getTotalColumns();
    }

}