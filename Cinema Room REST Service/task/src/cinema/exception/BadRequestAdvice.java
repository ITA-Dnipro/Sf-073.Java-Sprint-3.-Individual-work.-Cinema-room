package cinema.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@ControllerAdvice
public class BadRequestAdvice {

    @ResponseBody
    @ExceptionHandler(AlreadyPurchasedTicketException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<String, String> badRequestHandler(AlreadyPurchasedTicketException ex) {
        return Map.of("error", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(SeatOutOfBoundsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<String, String> badRequestHandler(SeatOutOfBoundsException ex) {
        return Map.of("error", ex.getMessage());
    }
}
