package antifraud.controller;

import antifraud.model.AccessOperation;
import antifraud.model.AccessResponse;
import antifraud.model.DeleteResponse;
import antifraud.model.RoleOperation;
import antifraud.model.UnauthorisedUser;
import antifraud.model.User;
import antifraud.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j

@RestController
public class UserController {
    final
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("api/auth/access")
    AccessResponse lockOrUnlockUser(@RequestBody AccessOperation accessOperation) {
        return userService.lockOrUnlockUser(accessOperation);
    }

    @PutMapping("/api/auth/role")
    User changeRole(@RequestBody RoleOperation roleOperation) {
        return userService.changeRole(roleOperation);
    }

    @PostMapping("/api/auth/user")
    @ResponseStatus(code = HttpStatus.CREATED)
    User save(@RequestBody UnauthorisedUser user) {
        return userService.saveUser(user);
    }

    @GetMapping("/api/auth/list")
    List<User> findAll() {
        return userService.findAll();
    }

    @DeleteMapping("/api/auth/user/{username}")
    DeleteResponse foo(@PathVariable String username) {
        return new DeleteResponse(userService.delete(username));
    }


}
