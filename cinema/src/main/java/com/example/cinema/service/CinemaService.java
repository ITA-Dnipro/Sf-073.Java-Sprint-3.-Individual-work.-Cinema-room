package com.example.cinema.service;

import com.example.cinema.model.CinemaRoom;
import com.example.cinema.model.Seat;
import com.example.cinema.model.SeatInfo;

public interface CinemaService {
    CinemaRoom getCinemaRoomInfo();
    SeatInfo purchase(Seat seat);
}
