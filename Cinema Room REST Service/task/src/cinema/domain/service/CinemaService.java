package cinema.domain.service;

import cinema.domain.model.CinemaRoom;
import cinema.domain.model.Seat;

public interface CinemaService {

    CinemaRoom getCinemaRoomInfo();

    Seat purchaseTicket(Seat seat);
}
