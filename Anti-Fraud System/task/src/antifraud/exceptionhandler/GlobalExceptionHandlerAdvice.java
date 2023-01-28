package antifraud.exceptionhandler;

import antifraud.exceptions.AccessViolationException;
import antifraud.exceptions.AlreadyProvidedException;
import antifraud.exceptions.CardNotFoundException;
import antifraud.exceptions.ExistingAdministratorException;
import antifraud.exceptions.ExistingCardException;
import antifraud.exceptions.ExistingIpException;
import antifraud.exceptions.ExistingUsernameException;
import antifraud.exceptions.IpNotFoundException;
import antifraud.exceptions.NonExistentRoleException;
import antifraud.rest.dto.CustomMessageDTO;
import antifraud.rest.dto.ErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

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

    @ExceptionHandler(LockedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<CustomMessageDTO> handleLockedException(LockedException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(401)
                .body(new CustomMessageDTO(ex.getMessage()));
    }

    @ExceptionHandler(ExistingUsernameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<CustomMessageDTO> handleExistingUsernameException(ExistingUsernameException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(409)
                .body(new CustomMessageDTO(ExceptionConstants.EXISTING_USERNAME));
    }

    @ExceptionHandler(ExistingIpException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<CustomMessageDTO> handleExistingIpException(ExistingIpException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(409)
                .body(new CustomMessageDTO(ExceptionConstants.EXISTING_IP));
    }

    @ExceptionHandler(ExistingCardException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<CustomMessageDTO> handleExistingCardException(ExistingCardException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(409)
                .body(new CustomMessageDTO(ExceptionConstants.EXISTING_CARD));
    }

    @ExceptionHandler(AlreadyProvidedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<CustomMessageDTO> handleAlreadyProvidedException(AlreadyProvidedException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(409)
                .body(new CustomMessageDTO(ExceptionConstants.SAME_ROLE));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomMessageDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.badRequest()
                .body(new CustomMessageDTO(ExceptionConstants.VALIDATION_FAIL));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomMessageDTO> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.badRequest()
                .body(new CustomMessageDTO(ex.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<CustomMessageDTO> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(404)
                .body(new CustomMessageDTO(ex.getMessage() + ExceptionConstants.USERNAME_NOT_FOUND));
    }

    @ExceptionHandler(IpNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<CustomMessageDTO> handleIpNotFoundException(IpNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(404)
                .body(new CustomMessageDTO(ex.getMessage() + ExceptionConstants.IP_NOT_FOUND));
    }

    @ExceptionHandler(CardNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<CustomMessageDTO> handleCardNotFoundException(CardNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(404)
                .body(new CustomMessageDTO(ex.getMessage() + ExceptionConstants.CARD_NOT_FOUND));
    }

    @ExceptionHandler(ExistingAdministratorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomMessageDTO> handleExistingAdministratorException(ExistingAdministratorException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.badRequest()
                .body(new CustomMessageDTO(ExceptionConstants.ADMIN));
    }

    @ExceptionHandler(AccessViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomMessageDTO> handleAccessViolationException(AccessViolationException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.badRequest()
                .body(new CustomMessageDTO(ExceptionConstants.CANNOT_BE_BLOCKED));
    }

    @ExceptionHandler(NonExistentRoleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomMessageDTO> handleNonExistentRoleException(NonExistentRoleException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.badRequest()
                .body(new CustomMessageDTO(ExceptionConstants.ROLE_NON_EXIST));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<CustomMessageDTO> handleErrors(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.internalServerError()
                .body(new CustomMessageDTO(ex.getMessage()));
    }
}