package cinema.rest.controller;

import cinema.domain.model.CinemaRoom;
import cinema.domain.model.Seat;
import cinema.domain.service.CinemaService;
import cinema.rest.dto.CinemaRoomDTO;
import cinema.rest.dto.SeatDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CinemaController {
    private final CinemaService cinemaService;

    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @GetMapping("/seats")
    public CinemaRoomDTO getCinemaRoom() {
        CinemaRoom cinemaRoom = cinemaService.getCinemaRoomInfo();
        return CinemaRoomDTO.fromModel(cinemaRoom);
    }

    @PostMapping("/purchase")
    public SeatDTO purchaseTicket(@RequestBody SeatDTO seatDTO) {
        Seat seat = cinemaService.purchaseTicket(seatDTO.toModel());
        return SeatDTO.fromModel(seat);
    }
}
