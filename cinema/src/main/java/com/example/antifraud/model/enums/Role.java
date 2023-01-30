package com.example.antifraud.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMINISTRATOR,
    MERCHANT,
    SUPPORT;
    final String role;
    Role() {
        this.role = "ROLE" + name();
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
