package cinema.service;

import cinema.model.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CinemaService {
    Ticket purchase(Seat seat);

    CinemaRoom getAllSeats();

    ReturnedTicket returnTicket(ReturnTicketRequest returnTicketRequest);

    Stats listStats(String statsLogin);
}
