package cinema.controllers;

import cinema.models.CinemaRoom;
import cinema.models.DTOs.SeatPriceDTO;
import cinema.models.Seat;
import cinema.services.CinemaRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CinemaRoomController {

    private final CinemaRoomService cinemaRoomService;

    @GetMapping("/seats")
    CinemaRoom availableSeats() {
        return cinemaRoomService.getCinemaRoomInfo();
    }

    @PostMapping("/purchase")
    SeatPriceDTO seatPrice(@RequestBody Seat seat) {
        return cinemaRoomService.purchase(seat);
    }
}
