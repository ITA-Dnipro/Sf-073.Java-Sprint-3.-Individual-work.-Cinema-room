package antifraud.domain.security;

import org.springframework.security.authentication.jaas.AuthorityGranter;

public enum UserRole {
    MERCHANT,
    ADMINISTRATOR,
    SUPPORT
}
