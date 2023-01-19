package com.example.cinema.controller;

import com.example.cinema.exception.BusinessException;
import com.example.cinema.model.ErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler
    ResponseEntity<ErrorDTO> errorHandler(BusinessException ex) {
        log.info("exception {}", ex.getMessage());
        return ResponseEntity.badRequest()
                .body(new ErrorDTO(ex.getMessage()));
    }

    @ExceptionHandler
    ResponseEntity<ErrorDTO> errorHandler(Exception ex) {
        log.info("exception {}", ex.getMessage());
        return ResponseEntity.internalServerError()
                .body(new ErrorDTO(ex.getMessage()));
    }
}
