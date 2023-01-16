package cinema.domain.service;

import cinema.domain.model.CinemaRoom;
import cinema.domain.model.Seat;
import cinema.domain.model.Ticket;
import cinema.domain.model.TokenInfo;

public interface CinemaService {

    CinemaRoom getCinemaRoomInfo();

    Ticket purchaseTicket(Seat seat);

    int calculateTicketPrice(Seat seat);

    Ticket returnTicket(TokenInfo tokenInfo);
}
