package cinema.controller;

import cinema.exceprion.AppError;
import cinema.model.CinemaRoom;
import cinema.model.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CinemaController {
    @Autowired
    private CinemaRoom cinemaRoom;

    @GetMapping("/seats")
    public CinemaRoom getSeats(){
        return cinemaRoom;
    }

    @PostMapping(value = "/purchase",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity purchase(@RequestBody Seat seat) {
        if(cinemaRoom.seatWasPurchased(seat)){
            return new ResponseEntity(new AppError(
                    "The ticket has been already purchased!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(!cinemaRoom.seatAreInAvailableSeats(seat)){
            return new ResponseEntity(new AppError(
                    "The number of a row or a column is out of bounds!"),
                    HttpStatus.BAD_REQUEST);
        }

        seat.setPrice(seat.calculatePrice());
        cinemaRoom.purchaseSeat(seat);
        return new ResponseEntity(seat,
                HttpStatus.OK);
    }
}