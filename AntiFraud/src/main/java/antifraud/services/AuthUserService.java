package antifraud.services;

import antifraud.domain.models.entity.UserEntity;
import antifraud.domain.security.AuthUserDetails;
import antifraud.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userRecord = userRepository.findByUserName(username);
        return userRecord.map(userEntity -> new AuthUserDetails(userRecord.get())).orElseThrow(()-> new UsernameNotFoundException(""));
    }
}
