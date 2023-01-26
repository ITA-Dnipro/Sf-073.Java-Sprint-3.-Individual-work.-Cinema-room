package antifraud.exceptionhandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException{
    @ExceptionHandler({ConstraintViolationException.class, IllegalArgumentException.class})
    ResponseEntity responseEntity(){
       return ResponseEntity.status(400).build();
    }
}
