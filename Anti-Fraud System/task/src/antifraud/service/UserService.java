package antifraud.service;

import antifraud.model.DeleteResponse;
import antifraud.model.UnauthorisedUser;
import antifraud.model.User;
import antifraud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepo;
    @Autowired
    PasswordEncoder encoder;
    public User saveUser(UnauthorisedUser unauthorisedUser){
        Optional<UnauthorisedUser> userNameEntry = userRepo.findByUsername(unauthorisedUser.getUsername());
        if(userNameEntry.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        unauthorisedUser.setRole("ROLE_USER");
        unauthorisedUser.setPassword(encoder.encode(unauthorisedUser.getPassword()));
        userRepo.save(unauthorisedUser);

        User authorisedUser = new User();
        authorisedUser.setId(unauthorisedUser.getId());
        authorisedUser.setName(unauthorisedUser.getName());
        authorisedUser.setUsername(unauthorisedUser.getUsername());
        return authorisedUser;
    }
    public List<User> findAll(){
        List<User> users = new ArrayList<>();
       for(var user: userRepo.findAll()){
           User user1 = new User();
           user1.setId(user.getId());
           user1.setName(user.getName());
           user1.setUsername(user.getUsername());
           users.add(user1);
       }
       return users;
    }

    public String delete(String username) {
        Optional<UnauthorisedUser> byUsername = userRepo.findByUsername(username);
        if(byUsername.isPresent()) {
            userRepo.deleteById(byUsername.get().getId());
            return byUsername.get().getUsername();
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}

