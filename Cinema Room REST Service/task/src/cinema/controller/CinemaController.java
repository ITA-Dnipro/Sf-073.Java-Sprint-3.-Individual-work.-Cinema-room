package cinema.controller;

import cinema.exception.WrongPassword;
import cinema.model.*;
import cinema.service.CinemaService;
import cinema.model.ReturnedTickedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    SoldTicket purchase(@RequestBody Seat seat){
        return  cinemaService.purchase(seat);
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorDTO> errorHandler(Exception ex){
        return ResponseEntity.badRequest().body(new ErrorDTO(ex.getMessage()));
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    ResponseEntity<ErrorDTO> wrongPass(WrongPassword ex){
        return ResponseEntity.status(401).body(new ErrorDTO(ex.getMessage()));
    }
    @PostMapping("/return")
    ReturnedTickedResponse returnTicket(@RequestBody TicketReturnRequest req){
        return cinemaService.returnedTicket(req.getToken());
    }
    @PostMapping(path = "/stats")
        Statistics returnedPasswordResponse(@RequestParam(value ="password",
            required = false) String password){

            return cinemaService.returnedStats(password);
        }
}
