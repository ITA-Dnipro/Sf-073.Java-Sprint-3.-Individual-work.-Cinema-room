package antifraud.service;

import antifraud.model.UnauthorisedUser;
import antifraud.model.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
public class UserDetailsImpl implements UserDetails {

    private final UnauthorisedUser user;
    private final List<GrantedAuthority> rolesAndAuthorities;

    public UserDetailsImpl(UnauthorisedUser user){
        this.user = user;
        rolesAndAuthorities = List.of(new SimpleGrantedAuthority("ROLE_"+user.getRole()));
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return rolesAndAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
        return user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
