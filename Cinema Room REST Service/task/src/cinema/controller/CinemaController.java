package cinema.controller;

import cinema.model.CinemaRoom;
import cinema.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController

public class CinemaController {
    @Autowired
     CinemaService cinemaService;
    @GetMapping("/seats")
    CinemaRoom getAvailableSeats(){
        return cinemaService.getCinemaRoomInfo(
        );
    }
}
