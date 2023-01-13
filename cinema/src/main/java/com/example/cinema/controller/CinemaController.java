package com.example.cinema.controller;

import com.example.cinema.model.CinemaRoom;
import com.example.cinema.model.Seat;
import com.example.cinema.model.SeatInfo;
import com.example.cinema.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CinemaController {

    final CinemaService cinemaService;

    @GetMapping("/seats")
    CinemaRoom getAvailableSeats() {
        return cinemaService.getCinemaRoomInfo();
    }

    @PostMapping("/purchase")
    SeatInfo purchase(@RequestBody Seat seat) {
        return cinemaService.purchase(seat);
    }
}
