package antifraud.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService extends org.springframework.security.core.userdetails.UserDetailsService {


    @Override
    org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
