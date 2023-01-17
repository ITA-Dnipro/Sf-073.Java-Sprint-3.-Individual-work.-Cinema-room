package cinema.services;

import cinema.models.CinemaRoom;
import cinema.models._DTOs.ReturnedTicketDTO;
import cinema.models._DTOs.SeatCoordinates;
import cinema.models._DTOs.SeatTokenDTO;
import cinema.models._DTOs.StatsDTO;

public interface CinemaRoomService {

    CinemaRoom getCinemaRoomInfo();

    SeatTokenDTO purchase(SeatCoordinates seat);

    ReturnedTicketDTO returnTicket(String token);

    StatsDTO calcStats();
}
