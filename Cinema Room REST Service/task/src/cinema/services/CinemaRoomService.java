package cinema.services;

import cinema.models.CinemaRoom;
import cinema.models.DTOs.SeatPriceDTO;
import cinema.models.Seat;

public interface CinemaRoomService {

    CinemaRoom getCinemaRoomInfo();

    SeatPriceDTO purchase(Seat seat);
}
