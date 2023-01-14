package cinema.rest.controller;

import cinema.domain.model.CinemaRoom;
import cinema.domain.service.CinemaService;
import cinema.rest.dto.CinemaRoomDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CinemaController {
    private final CinemaService cinemaService;

    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @GetMapping("/seats")
    public CinemaRoomDTO getAvailableSeats() {
        CinemaRoom cinemaRoom = cinemaService.getAvailableSeats();

        return CinemaRoomDTO.fromModel(cinemaRoom);
    }
}
