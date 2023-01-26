package antifraud.domain.model;

import antifraud.domain.model.constants.UserAccess;
import antifraud.domain.model.constants.UserRole;

public interface User {
    Long getId();

    String getName();

    void setName(String name);

    String getUsername();

    void setUsername(String username);

    String getPassword();

    void setPassword(String password);

    UserRole getRole();

    void setRole(UserRole role);

    UserAccess getAccess();

    void setAccess(UserAccess access);

    boolean isAccountNonLocked();
}