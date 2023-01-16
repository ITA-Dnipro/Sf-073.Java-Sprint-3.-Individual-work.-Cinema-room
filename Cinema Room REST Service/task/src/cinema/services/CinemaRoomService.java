package cinema.services;

import cinema.models.CinemaRoom;
import cinema.models._model_DTOs.ReturnedTicketDTO;
import cinema.models._model_DTOs.SeatCoordinates;
import cinema.models._model_DTOs.SeatTokenDTO;

public interface CinemaRoomService {

    CinemaRoom getCinemaRoomInfo();

    SeatTokenDTO purchase(SeatCoordinates seat);

    ReturnedTicketDTO returnTicket(String token);
}
