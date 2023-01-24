package antifraud.domain.service.impl;

import antifraud.domain.model.CustomUser;
import antifraud.domain.model.User;
import antifraud.domain.service.UserService;
import antifraud.persistence.repository.CustomUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final CustomUserRepository customUserRepository;

    @Override
    public Optional<User> registerUser(User userCredentials) {
        return customUserRepository.existsCustomUserByUsername(userCredentials.getUsername()) ?
                Optional.empty() :
                Optional.of(customUserRepository.save((CustomUser) userCredentials));
    }
}