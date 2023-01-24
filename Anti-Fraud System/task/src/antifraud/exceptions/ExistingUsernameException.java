package antifraud.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ExistingUsernameException extends ResponseStatusException {
    public ExistingUsernameException(HttpStatus status) {
        super(status);
    }

    public ExistingUsernameException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public ExistingUsernameException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }

    public ExistingUsernameException(int rawStatusCode, String reason, Throwable cause) {
        super(rawStatusCode, reason, cause);
    }

}
