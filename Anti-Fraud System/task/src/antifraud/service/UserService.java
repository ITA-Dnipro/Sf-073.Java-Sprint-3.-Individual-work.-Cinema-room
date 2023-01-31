package antifraud.service;

import antifraud.model.*;
import antifraud.repository.UserRepository;
import antifraud.roles.Roles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    final UserRepository userRepo;
    final
    PasswordEncoder encoder;

    public UserService(UserRepository userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }
    public AccessResponse lockOrUnlockUser(AccessOperation accessOperation){
        Optional<UnauthorisedUser> unauthorisedUser = userRepo.findByUsername(accessOperation.getUsername());
        if(unauthorisedUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        else if(unauthorisedUser.get().getRole().equals("ADMINISTRATOR")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        switch (accessOperation.getOperation()) {
            case "LOCK" -> {
                unauthorisedUser.get().setAccountNonLocked(false);
                userRepo.save(unauthorisedUser.get());
            }
            case "UNLOCK" -> {
                unauthorisedUser.get().setAccountNonLocked(true);
                userRepo.save(unauthorisedUser.get());
            }
        }

        var result = accessOperation.getOperation().equals("LOCK")
                ? "locked!": "unlocked!";
        return new AccessResponse("User " + accessOperation.getUsername() + " " + result);
    }
    public User saveUser(UnauthorisedUser unauthorisedUser){
        Optional<UnauthorisedUser> userNameEntry = userRepo.findByUsername(unauthorisedUser.getUsername());
        if(userNameEntry.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        unauthorisedUser.setPassword(encoder.encode(unauthorisedUser.getPassword()));
        unauthorisedUser.setRole(Roles.MERCHANT.toString());
        unauthorisedUser.setAccountNonLocked(false);
        userRepo.save(unauthorisedUser);
        if(unauthorisedUser.getId()==1){
            unauthorisedUser.setRole(Roles.ADMINISTRATOR.toString());
            unauthorisedUser.setAccountNonLocked(true);
            userRepo.save(unauthorisedUser);
        }

        User authorisedUser = new User();
        authorisedUser.setId(unauthorisedUser.getId());
        authorisedUser.setName(unauthorisedUser.getName());
        authorisedUser.setUsername(unauthorisedUser.getUsername());
        authorisedUser.setRole(unauthorisedUser.getRole());
        return authorisedUser;
    }
    public List<User> findAll(){
        List<User> users = new ArrayList<>();
       for(var user: userRepo.findAll()){
           User user1 = new User(user.getId(),user.getName(), user.getUsername(), user.getRole());
           /*user1.setId(user.getId());
           user1.setName(user.getName());
           user1.setUsername(user.getUsername());
           user1.setRole(user.getRole());*/
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

    public User changeRole(RoleOperation roleOperation) {
        Optional<UnauthorisedUser> byUsername = userRepo.findByUsername(roleOperation.getUsername());
        if(byUsername.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        else if(byUsername.get().getRole().equals( roleOperation.getRole())){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        else if(!(roleOperation.getRole().equals("SUPPORT")||roleOperation.getRole().equals("MERCHANT"))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        byUsername.get().setRole(roleOperation.getRole());
        userRepo.save(byUsername.get());
        User user = new User();
        user.setRole(byUsername.get().getRole());
        user.setName(byUsername.get().getName());
        user.setId(byUsername.get().getId());
        user.setUsername(byUsername.get().getUsername());

        return user;
    }
}

