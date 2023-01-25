package antifraud.exceptionhandler;

import antifraud.exceptions.ExistingUsernameException;
import antifraud.rest.dto.CustomMessageDTO;
import antifraud.rest.dto.ErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandlerAdvice {

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorDTO> handleAuthenticationException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorDTO(HttpStatus.UNAUTHORIZED.toString(), ExceptionConstants.FAILED_AUTH));
    }

    @ExceptionHandler(ExistingUsernameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<CustomMessageDTO> handleExistingUsernameException(ExistingUsernameException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(409)
                .body(new CustomMessageDTO(ExceptionConstants.EXISTING_USERNAME));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<CustomMessageDTO> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(404)
                .body(new CustomMessageDTO(ex.getMessage() + ExceptionConstants.USERNAME_NOT_FOUND));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomMessageDTO> handleNotValidException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.badRequest()
                .body(new CustomMessageDTO(ExceptionConstants.VALIDATION_FAIL));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<CustomMessageDTO> handleErrors(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.internalServerError()
                .body(new CustomMessageDTO(ex.getMessage()));
    }
}