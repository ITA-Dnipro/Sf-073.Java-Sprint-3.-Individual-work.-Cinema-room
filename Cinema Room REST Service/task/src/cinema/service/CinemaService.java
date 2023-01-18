package cinema.service;

import cinema.model.CinemaRoom;
import cinema.model.Seat;
import cinema.model.request.SeatRequest;
import cinema.model.response.ReturnedTicketResponse;
import cinema.model.response.StatisticResponse;
import cinema.model.response.TicketResponse;

public interface CinemaService {
    CinemaRoom getCinemaInfo();

    TicketResponse purchaseSeat(Seat seat);

    ReturnedTicketResponse returnTicket(String token);

    Seat mapSeatDTOToEntity(SeatRequest seatDTO);

    boolean authorizationIsValid(String password);

    StatisticResponse getStats();
}
