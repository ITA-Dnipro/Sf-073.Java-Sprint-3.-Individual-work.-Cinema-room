package cinema.service;

import cinema.model.*;
import org.springframework.stereotype.Service;

@Service
public interface CinemaService {
    Ticket purchase(Seat seat);

    CinemaRoom getAllSeats();

    ReturnedTicket returnTicket(ReturnTicketRequest returnTicketRequest);
}
