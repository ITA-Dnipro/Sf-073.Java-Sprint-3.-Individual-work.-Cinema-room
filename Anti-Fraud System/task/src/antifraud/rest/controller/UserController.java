package antifraud.rest.controller;

import antifraud.domain.model.User;
import antifraud.domain.service.UserService;
import antifraud.exceptions.ExistingUsernameException;
import antifraud.rest.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/user")
    UserDTO createUser(@RequestBody @Valid UserDTO userDTO) {
        User registeredUser = userService.registerUser(userDTO.toModel())
                .orElseThrow(() -> new ExistingUsernameException(HttpStatus.CONFLICT));
        return UserDTO.fromModel(registeredUser);
    }
}
