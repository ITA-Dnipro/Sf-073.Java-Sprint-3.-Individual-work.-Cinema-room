package cinema.persistence.repository;

import cinema.config.CinemaProperties;
import cinema.domain.model.Seat;
import cinema.domain.model.Ticket;
import cinema.domain.model.TokenInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
public class SeatRepository {
    private final CinemaProperties cinemaProperties;
    private final List<Ticket> tickets = new ArrayList<>();
    private List<Seat> seats;

    public SeatRepository(CinemaProperties cinemaProperties) {
        this.cinemaProperties = cinemaProperties;
    }

    public void saveCinemaRoom(List<Seat> seats) {
        this.seats = seats;
    }

    public List<Seat> getAvailableSeats() {
        return seats.stream()
                .filter(Seat::isAvailable)
                .toList();
    }

    public Optional<Ticket> save(Seat seat) {
        boolean seatAvailable = isSeatAvailable(seat);
        if (seatAvailable) {
            Ticket ticket = new Ticket(String.valueOf(UUID.randomUUID()), seat);
            tickets.add(ticket);
            return Optional.of(ticket);
        }
        return Optional.empty();
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

    public Optional<Ticket> delete(TokenInfo tokenInfo) {
        Optional<Ticket> ticket = tickets.stream()
                .filter(t -> t.getToken().equals(tokenInfo.getToken()))
                .findFirst();

        if (ticket.isPresent()) {
            Seat seatFromTicket = ticket.get().getSeat();
            seats.stream()
                    .filter(s -> s.getRowPosition() == seatFromTicket.getRowPosition() &&
                            s.getColumnPosition() == seatFromTicket.getColumnPosition())
                    .findFirst()
                    .ifPresent(s -> s.setAvailable(true));
            tickets.remove(ticket.get());
        }
        return ticket;
    }

    public boolean isSeatPresent(Seat seat) {
        return seat.getRowPosition() > 0 &&
                seat.getRowPosition() <= cinemaProperties.getTotalRows() &&
                seat.getColumnPosition() > 0 &&
                seat.getColumnPosition() <= cinemaProperties.getTotalColumns();
    }

}