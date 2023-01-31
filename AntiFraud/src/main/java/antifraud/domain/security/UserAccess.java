package antifraud.domain.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserAccess {

    LOCKED("locked"),
    UNLOCKED("unlocked");
    private final String status;
}
