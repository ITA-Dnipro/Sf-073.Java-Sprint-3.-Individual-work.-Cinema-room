package stage3.four.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WrongTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleWrongTokenException() {
        return new ErrorResponse(new WrongTokenException().getMessage());
    }

    @ExceptionHandler(TicketAlreadyPurchasedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleTicketAlreadyPurchasedException() {
        return new ErrorResponse(new TicketAlreadyPurchasedException().getMessage());
    }

    @ExceptionHandler(MissingOrWrongPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleMissingOrWrongPasswordException() {
        return new ErrorResponse(new MissingOrWrongPasswordException().getMessage());
    }

    @ExceptionHandler(SeatsOutOfBoundsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleSeatsOutOfBoundsException() {
        return new ErrorResponse(new SeatsOutOfBoundsException().getMessage());
    }

}
