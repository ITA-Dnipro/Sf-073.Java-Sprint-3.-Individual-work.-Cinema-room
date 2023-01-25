package antifraud.controller;

import antifraud.model.DeleteResponse;
import antifraud.model.UnauthorisedUser;
import antifraud.model.User;
import antifraud.repository.UserRepository;
import antifraud.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Slf4j

@RestController
public class UserController {
    final
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/api/auth/user")
    @ResponseStatus(code = HttpStatus.CREATED)
    User save(@RequestBody  UnauthorisedUser user){
           return userService.saveUser(user);
    }
    @GetMapping("/api/auth/list")
    List<User> findAll(){
        return userService.findAll();
    }
    @DeleteMapping("/api/auth/user/{username}")
    DeleteResponse foo(@PathVariable String username){
        return new DeleteResponse(userService.delete(username));
    }


}
