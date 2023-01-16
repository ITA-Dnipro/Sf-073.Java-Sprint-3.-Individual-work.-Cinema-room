package cinema.service;

import cinema.model.*;
import org.springframework.http.ResponseEntity;

public interface CinemaService {
    CinemaRoom getCinemaRoomInfo();

    SoldTicket purchase(Seat seat);
    ReturnedTickedResponse returnedTicket(String token);

    Statistics returnedStats(String password);
}
