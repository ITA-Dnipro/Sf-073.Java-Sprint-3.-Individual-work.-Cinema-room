package antifraud.service;

import antifraud.model.UnauthorisedUser;
import antifraud.model.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
public class UserDetailsImpl implements UserDetails, org.springframework.security.core.userdetails.UserDetails {

    private final String username;
    private final String password;
    private final List<GrantedAuthority> rolesAndAuthorities;

    public UserDetailsImpl(UnauthorisedUser user){
        username = user.getUsername();
        password = user.getPassword();
        rolesAndAuthorities = List.of(new SimpleGrantedAuthority(user.getRole()));
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return rolesAndAuthorities;
    }

    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
