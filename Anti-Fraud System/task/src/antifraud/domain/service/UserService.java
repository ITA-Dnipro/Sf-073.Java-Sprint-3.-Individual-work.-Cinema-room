package antifraud.domain.service;

import antifraud.domain.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> registerUser(User userCredentials);
}
