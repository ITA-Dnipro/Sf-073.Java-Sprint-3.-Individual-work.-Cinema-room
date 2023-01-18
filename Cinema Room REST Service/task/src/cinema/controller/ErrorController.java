package cinema.controller;

import cinema.exceprion.BusinessException;
import cinema.exceprion.WrongAuthorizationException;
import cinema.model.response.AppError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    AppError errorHandler(BusinessException exception) {
        log.info("exception {}", exception);
        return new AppError(exception.getMessage());
    }

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    AppError errorHandler(WrongAuthorizationException exception) {
        log.info("exception {}", exception);
        return new AppError(exception.getMessage());
    }
}
