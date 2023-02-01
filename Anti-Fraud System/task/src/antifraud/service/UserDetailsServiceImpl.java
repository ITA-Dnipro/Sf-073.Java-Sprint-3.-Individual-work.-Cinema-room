package antifraud.service;

import antifraud.model.UnauthorisedUser;
import antifraud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UnauthorisedUser> unauthorisedUser = userRepository.findByUsername(username);
        if (unauthorisedUser.isPresent()) {
            return new UserDetailsImpl(unauthorisedUser.get());
        }
        throw new UsernameNotFoundException("User with " + username + " not found");
    }
}
