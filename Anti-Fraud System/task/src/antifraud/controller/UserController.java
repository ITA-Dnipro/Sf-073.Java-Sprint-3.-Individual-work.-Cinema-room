package antifraud.controller;

import antifraud.model.UnauthorisedUser;
import antifraud.model.User;
import antifraud.repository.UserRepository;
import antifraud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/api/auth/user")
    @ResponseStatus(code = HttpStatus.CREATED)
    User save(@RequestBody UnauthorisedUser user){
        Optional<UnauthorisedUser> userNameEntry = userRepository.findByUsername(user.getUsername());
        if(userNameEntry.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
            userService.saveUser(user);
            User user1 = new User();
            user1.setId(user.getId());
            user1.setName(user.getName());
            user1.setUsername(user.getUsername());
            return user1;
    }
    @GetMapping("/api/auth/list")
    List<User> findAll(){
        List<User> users = new ArrayList<>();
        for(var user: userService.users()){
            User user1 = new User();
            user1.setId(user.getId());
            user1.setName(user.getName());
            user1.setUsername(user.getUsername());
            users.add(user1);
        }
        return users;
    }


}
