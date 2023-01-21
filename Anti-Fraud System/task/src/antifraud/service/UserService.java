package antifraud.service;

import antifraud.model.UnauthorisedUser;
import antifraud.model.User;
import antifraud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepo;
    @Autowired
    PasswordEncoder encoder;
    public UnauthorisedUser saveUser(UnauthorisedUser user){
        user.setRole("ROLE_USER");
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
    public List<UnauthorisedUser> users(){
       return userRepo.findAll();
    }
}

