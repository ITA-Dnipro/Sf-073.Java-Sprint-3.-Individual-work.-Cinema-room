package antifraud.rest.controller;

import antifraud.domain.model.User;
import antifraud.domain.service.UserService;
import antifraud.rest.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/api/auth/user")
    UserDTO validateUser(@RequestBody @Valid UserDTO userDTO) {
        User registeredUser = userService.registerUser(userDTO.toModel());
        return UserDTO.fromModel(registeredUser);
    }
}
