package antifraud.service;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface UserDetails {
    String getUsername();
    String getPassword();
    Collection<GrantedAuthority> getAuthorities();
    boolean isEnabled();
    boolean isAccountNonExpired();
    boolean isAccountNonLocked();
    boolean isCredentialsNonExpired();

}
