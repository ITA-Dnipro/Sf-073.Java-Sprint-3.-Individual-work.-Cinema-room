package cinema.controllers;

import cinema.model.*;
import cinema.service.CinemaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
public class CinemaController {
    private final CinemaService cinemaService;

    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @GetMapping("/seats")
    CinemaRoom getAllSeats() {
        return cinemaService.getAllSeats();
    }

    @PostMapping("/purchase")
    Ticket buyTicket(@RequestBody Seat seat) {
        return cinemaService.purchase(seat);
    }

    @PostMapping("/return")
    ReturnedTicket returnTicket(@RequestBody ReturnTicketRequest returnTicketRequest){
        return cinemaService.returnTicket(returnTicketRequest);
    }

    @PostMapping("/stats")
    Stats listStats(@RequestParam(required=false,value = "password") String password){
        return cinemaService.listStats(password);
    }
}
