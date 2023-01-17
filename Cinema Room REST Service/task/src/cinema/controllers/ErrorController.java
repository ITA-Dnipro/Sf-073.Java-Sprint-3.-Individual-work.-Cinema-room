package cinema.controllers;

import cinema.exceptions.BusinessException;
import cinema.exceptions.NotAuthorizedException;
import cinema.exceptions._exception_DTOs.ErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorController {

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    ErrorDTO errorHandler(BusinessException exception) {
//        log.info("exception: {}", exception.getMessage());
//        return new ErrorDTO(exception.getMessage());
//        // can be done with Map<String,String> -> Map.of("error", exception.getMessage()) or ResponseEntity<>
//    }


    @ExceptionHandler
    ResponseEntity<ErrorDTO> errorHandler(BusinessException exception) {
        log.info("exception: {}", exception.getMessage());
        return ResponseEntity.badRequest()
                .body(new ErrorDTO(exception.getMessage()));
        // can be done with Map<String,String> -> Map.of("error", exception.getMessage())
    }

    @ExceptionHandler
    ResponseEntity<ErrorDTO> errorHandler(NotAuthorizedException exception) {
        log.info("exception: {}", exception.getMessage());
        return ResponseEntity.status(401)
                .body(new ErrorDTO(exception.getMessage()));

    }
}
