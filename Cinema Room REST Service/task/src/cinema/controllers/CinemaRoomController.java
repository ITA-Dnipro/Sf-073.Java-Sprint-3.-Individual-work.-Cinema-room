package cinema.controllers;

import cinema.exceptions.NotAuthorizedException;
import cinema.models.CinemaRoom;
import cinema.models._DTOs.*;
import cinema.models.ReturnTicketRequest;
import cinema.services.CinemaRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Slf4j(topic = "CinemaController-logger")
@RestController
@RequiredArgsConstructor
public class CinemaRoomController {

    private final CinemaRoomService cinemaRoomService;

    @GetMapping("/seats")
    CinemaRoom availableSeats() {
        return cinemaRoomService.getCinemaRoomInfo();
    }

    @PostMapping("/purchase")
    SeatTokenDTO seatPrice(@RequestBody SeatCoordinates seat) {
        return cinemaRoomService.purchase(seat);
    }

    @PostMapping("/return")
    ReturnedTicketDTO returnTicket(@RequestBody ReturnTicketRequest request) {
        return cinemaRoomService.returnTicket(request.getToken());
    }

    @PostMapping("/stats")
    StatsDTO stats(@RequestParam Optional<String> password) {
        return password.filter("super_secret"::equals)
                .map(s ->cinemaRoomService.calcStats())
                .orElseThrow(NotAuthorizedException::new);
    }
}
