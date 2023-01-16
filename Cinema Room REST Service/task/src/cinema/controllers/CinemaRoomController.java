package cinema.controllers;

import cinema.exceptions.BusinessException;
import cinema.models.CinemaRoom;
import cinema.models.DTOs.ErrorDTO;
import cinema.models.DTOs.SeatPriceDTO;
import cinema.models.DTOs.SeatCoordinates;
import cinema.services.CinemaRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


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
    SeatPriceDTO seatPrice(@RequestBody SeatCoordinates seat) {
        return cinemaRoomService.purchase(seat);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorDTO errorHandler(BusinessException exception) {
        log.info("exception: {}", exception.getMessage());
        return new ErrorDTO(exception.getMessage());
        // can be done with Map<String,String> -> Map.of("error", exception.getMessage())
    }
}
