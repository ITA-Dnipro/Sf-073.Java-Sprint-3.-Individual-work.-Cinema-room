package com.example.cinema.controller;

import com.example.cinema.exception.BusinessException;
import com.example.cinema.model.*;
import com.example.cinema.service.CinemaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    SeatInfo purchase(@RequestBody SeatCoordinates seat) {
        return cinemaService.purchase(seat);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorDTO> errorDTOResponseEntity(BusinessException ex) {
        log.info("exception {}",ex.getMessage());
        return ResponseEntity.badRequest().body(new ErrorDTO(ex.getMessage()));
    }
}
