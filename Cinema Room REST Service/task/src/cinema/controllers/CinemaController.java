package cinema.controllers;


import cinema.model.*;
import cinema.service.CinemaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    Ticket buyTicket(@RequestBody Seat seat) {
        return cinemaService.purchase(seat);
    }

    @PostMapping("/return")
    ReturnedTicket returnTicket(@RequestBody ReturnTicketRequest returnTicketRequest){

        System.out.println(returnTicketRequest);

        return cinemaService.returnTicket(returnTicketRequest);
    }

}
