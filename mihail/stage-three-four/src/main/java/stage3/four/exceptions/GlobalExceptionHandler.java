package stage3.four.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WrongToken.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleWrongTokenException() {
        return new ErrorResponse(new WrongToken().getMessage());
    }

    @ExceptionHandler(TicketAlreadyPurchased.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleTicketAlreadyPurchasedException() {
        return new ErrorResponse(new TicketAlreadyPurchased().getMessage());
    }

    @ExceptionHandler(MissingOrWrongPassword.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleMissingOrWrongPasswordException() {
        return new ErrorResponse(new MissingOrWrongPassword().getMessage());
    }

    @ExceptionHandler(SeatsOutOfBounds.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleSeatsOutOfBoundsException() {
        return new ErrorResponse(new SeatsOutOfBounds().getMessage());
    }

}
