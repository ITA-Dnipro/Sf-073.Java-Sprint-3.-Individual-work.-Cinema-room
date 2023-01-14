package cinema.controllers;

import cinema.models.CinemaRoom;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CinemaRoomController {

    @GetMapping("/seats")
    CinemaRoom room() {
        return new CinemaRoom();
    }
}
