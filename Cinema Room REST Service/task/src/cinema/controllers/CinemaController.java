package cinema.controllers;


import cinema.model.CinemaRoom;
import cinema.model.Seat;
import cinema.model.SeatInfo;
import cinema.service.CinemaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class CinemaController {
    @Autowired
    CinemaService cinemaService;

    @GetMapping("/seats")
    CinemaRoom getAllSeats() {
        return cinemaService.getAllSeats();
    }

    @PostMapping("/purchase")
    SeatInfo buyTicket(@RequestBody Seat seat) {
        return cinemaService.purchase(seat);
    }



}
