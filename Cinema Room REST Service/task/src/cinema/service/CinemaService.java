package cinema.service;

import cinema.model.*;

public interface CinemaService {
    CinemaRoom getCinemaRoomInfo();

    SoldTicket purchase(Seat seat);
    ReturnedTickedResponse returnedTicket(String token);

}
