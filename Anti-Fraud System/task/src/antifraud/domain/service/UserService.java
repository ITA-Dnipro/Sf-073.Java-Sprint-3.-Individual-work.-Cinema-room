package antifraud.domain.service;

import antifraud.domain.model.User;

public interface UserService {
    User registerUser(User userCredentials);
}
