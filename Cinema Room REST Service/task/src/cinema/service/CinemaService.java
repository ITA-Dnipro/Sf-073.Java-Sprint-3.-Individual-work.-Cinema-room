package cinema.service;

import cinema.model.CinemaRoom;
import cinema.model.Seat;
import cinema.model.SeatInfo;
import org.springframework.stereotype.Service;

@Service
public interface CinemaService {
    SeatInfo purchase(Seat seat);

    CinemaRoom getAllSeats();
}
