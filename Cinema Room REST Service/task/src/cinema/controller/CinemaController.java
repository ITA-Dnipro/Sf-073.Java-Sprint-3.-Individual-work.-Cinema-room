package cinema.controller;

import cinema.exceprion.WrongAuthorizationException;
import cinema.model.CinemaRoom;
import cinema.model.Seat;
import cinema.model.request.ReturnTicketRequest;
import cinema.model.request.SeatRequest;
import cinema.model.response.ReturnedTicketResponse;
import cinema.model.response.StatisticResponse;
import cinema.model.response.TicketResponse;
import cinema.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CinemaController {
    private final CinemaService cinemaService;

    @GetMapping("/seats")
    public CinemaRoom getSeats(){
        return cinemaService.getCinemaInfo();
    }

    @PostMapping("/purchase")
    public TicketResponse purchase(@RequestBody SeatRequest seatDTO) {
        Seat seat = cinemaService.mapSeatDTOToEntity(seatDTO);
        return cinemaService.purchaseSeat(seat);
    }

    @PostMapping("/return")
    ReturnedTicketResponse returnTicket(@RequestBody ReturnTicketRequest ticket) {
        return cinemaService.returnTicket(ticket.getToken());
    }

    @PostMapping("/stats")
    StatisticResponse statistics(@RequestParam(required = false) String password) {
        if (!cinemaService.authorizationIsValid(password)) {
            throw new WrongAuthorizationException("The password is wrong!");
        }
        return cinemaService.getStats();
    }

}