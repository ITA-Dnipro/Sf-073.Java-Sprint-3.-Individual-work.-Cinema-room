package cinema.controllers;

import cinema.ErrorDTO;
import cinema.exeption.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(BusinessException.class)
    ResponseEntity<ErrorDTO> errorHandler(BusinessException ex){
        log.info(ex.getMessage());
        return ResponseEntity.badRequest().body(new ErrorDTO(ex.getMessage()));
    }
}
