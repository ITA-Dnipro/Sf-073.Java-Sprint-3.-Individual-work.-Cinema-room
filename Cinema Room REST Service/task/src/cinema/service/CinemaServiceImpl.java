package cinema.service;

import cinema.exeption.AlreadyBookedException;
import cinema.exeption.BusinessException;
import cinema.exeption.SeatOutOfBoundsException;
import cinema.model.CinemaRoom;
import cinema.model.Seat;
import cinema.model.SeatInfo;
import cinema.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CinemaServiceImpl implements CinemaService {
    @Autowired
    SeatRepository seatRepository;

    public SeatInfo purchase(Seat seat) {

        if (seatRepository.isBooked(seat)) {
            throw new AlreadyBookedException();
        }
        if (!seatRepository.isSeatPresent(seat)){
            throw new SeatOutOfBoundsException();
        }
        seatRepository.markSeatAsBooked(seat);
        return new SeatInfo(seat.row, seat.column);
    }

    @Override
    public CinemaRoom getAllSeats() {
        CinemaRoom room = new CinemaRoom(seatRepository.getAvailableSeats().stream()
                .map(this::seatToSeatInfo).toList());
        System.out.println(room.seats);

        System.out.println(seatRepository.getAvailableSeats());
        return new CinemaRoom(seatRepository.getAvailableSeats().stream()
                .map(this::seatToSeatInfo).toList());
    }

    private SeatInfo seatToSeatInfo(Seat seat) {
        return new SeatInfo(seat.row, seat.column);
    }
}
