package antifraud.domain.service.impl;

import antifraud.domain.model.CustomUser;
import antifraud.domain.model.User;
import antifraud.domain.service.UserService;
import antifraud.persistence.repository.CustomUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final CustomUserRepository userRepository;

    @Override
    public User registerUser(User userCredentials) {
        return userRepository.save((CustomUser) userCredentials);
    }
}