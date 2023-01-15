package cinema.service;

import cinema.model.CinemaRoom;
import cinema.model.Seat;
import cinema.model.SeatInfo;

public interface CinemaService {
    CinemaRoom getCinemaRoomInfo();

    SeatInfo purchase(Seat seat);
}
