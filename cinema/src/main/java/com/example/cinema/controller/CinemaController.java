package com.example.cinema.controller;


import com.example.cinema.exception.NotAuthorizedException;
import com.example.cinema.model.*;
import com.example.cinema.service.CinemaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j(topic="CINEMA_TOPIC")
@RequiredArgsConstructor
@RestController
public class CinemaController {

    final CinemaService cinemaService;

    @GetMapping("/seats")
    CinemaRoom getAvailableSeats() {
        return cinemaService.getCinemaRoomInfo();
    }

    @PostMapping("/purchase")
    SoldTicket purchase(@RequestBody SeatCoordinates seat) {
        return cinemaService.purchase(seat);
    }

    @PostMapping("/return")
    ReturnedTicketResponse purchaseReturn(@RequestBody TicketReturnRequest request) {
        return cinemaService.returnTicket(request.getToken());
    }

    @PostMapping("/stats")
    Stats stats(@RequestParam Optional<String> password) {
        return password.filter("super_secret"::equals)
                .map(s -> cinemaService.calcStats())
                .orElseThrow(NotAuthorizedException::new);
    }
}
