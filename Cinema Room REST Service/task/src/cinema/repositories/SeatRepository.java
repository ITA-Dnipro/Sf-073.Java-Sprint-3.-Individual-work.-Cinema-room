package cinema.repositories;

import cinema.config.CinemaProperties;
import cinema.exceptions.BusinessException;
import cinema.exceptions.OutOfBoundsException;
import cinema.models._model_DTOs.SeatCoordinates;
import cinema.models.Seat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public Seat sell(SeatCoordinates seat, int price) {
        Seat seatEntity = seats.stream()
                .filter(s -> s.getRow() == seat.getRow() &&
                        s.getColumn() == seat.getColumn())
                .findFirst()
                .orElseThrow(OutOfBoundsException::new);

        seatEntity.setSellPrice(price);
        seatEntity.setToken(UUID.randomUUID().toString());
        return seatEntity;
    }


    public Optional<Seat> getSeatByToken(String token) {
        return seats.stream()
                .filter(s -> token.equals(s.getToken()))
                .findFirst();

    }

    public void setAsAvailable(SeatCoordinates seat) {
        Seat seatEntity = seats.stream()
                .filter(s -> s.getRow() == seat.getRow() &&
                        s.getColumn() == seat.getColumn())
                .findFirst().orElseThrow(BusinessException::new);

        seatEntity.setToken(null);
        seatEntity.setSellPrice(null);
    }
}
