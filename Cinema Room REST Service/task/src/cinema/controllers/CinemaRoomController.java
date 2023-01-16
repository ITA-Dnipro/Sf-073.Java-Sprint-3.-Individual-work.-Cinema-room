package cinema.controllers;

import cinema.exceptions.BusinessException;
import cinema.exceptions._exception_DTOs.ErrorDTO;
import cinema.models.CinemaRoom;
import cinema.models._model_DTOs.*;
import cinema.models.ReturnTicketRequest;
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
    SeatTokenDTO seatPrice(@RequestBody SeatCoordinates seat) {
        return cinemaRoomService.purchase(seat);
    }

    @PostMapping("/return")
    ReturnedTicketDTO returnTicket(@RequestBody ReturnTicketRequest request) {
        return cinemaRoomService.returnTicket(request.getToken());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorDTO errorHandler(BusinessException exception) {
        log.info("exception: {}", exception.getMessage());
        return new ErrorDTO(exception.getMessage());
        // can be done with Map<String,String> -> Map.of("error", exception.getMessage())
    }
}
