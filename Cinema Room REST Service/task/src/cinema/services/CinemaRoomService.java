package cinema.services;

import cinema.models.CinemaRoom;
import cinema.models.DTOs.SeatPriceDTO;
import cinema.models.DTOs.SeatCoordinates;

public interface CinemaRoomService {

    CinemaRoom getCinemaRoomInfo();

    SeatPriceDTO purchase(SeatCoordinates seat);
}
