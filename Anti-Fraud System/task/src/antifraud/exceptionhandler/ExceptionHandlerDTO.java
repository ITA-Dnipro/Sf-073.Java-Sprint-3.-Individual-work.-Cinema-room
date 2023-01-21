package antifraud.exceptionhandler;

import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ExceptionHandlerDTO {
    @ExceptionHandler
    ResponseEntity responseEntity(){
       return ResponseEntity.status(409).build();
    }

}
