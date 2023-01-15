package cinema.controller;

import cinema.model.CinemaRoom;
import cinema.model.ErrorDTO;
import cinema.model.Seat;
import cinema.model.SeatInfo;
import cinema.service.CinemaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CinemaController {
    @Autowired
     CinemaService cinemaService;
    @GetMapping("/seats")
    CinemaRoom getAvailableSeats(){
        return cinemaService.getCinemaRoomInfo(
        );
    }
    @PostMapping("/purchase")
    SeatInfo purchase(@RequestBody Seat seat){
        return  cinemaService.purchase(seat);
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorDTO> errorHandler(Exception ex){
        return ResponseEntity.badRequest().body(new ErrorDTO(ex.getMessage()));
    }
}
