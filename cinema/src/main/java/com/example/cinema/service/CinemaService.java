package com.example.cinema.service;

import com.example.cinema.model.*;

public interface CinemaService {
    CinemaRoom getCinemaRoomInfo();

    SoldTicket purchase(SeatCoordinates seat);

    ReturnedTicketResponse returnTicket(String token);

    Stats calcStats();
}
