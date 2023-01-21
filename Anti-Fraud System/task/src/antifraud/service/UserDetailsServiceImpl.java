package antifraud.service;

import antifraud.model.UnauthorisedUser;
import antifraud.model.User;
import antifraud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    UserRepository userRepository;


    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UnauthorisedUser user = userRepository.findByUsername(username).get();
        if(user==null){
            throw new UsernameNotFoundException("az");
        }
         return new UserDetailsImpl(user);
    }
}
