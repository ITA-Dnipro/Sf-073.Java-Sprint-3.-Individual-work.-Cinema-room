package antifraud.domain.service.impl;

import antifraud.domain.model.CustomUser;
import antifraud.domain.model.User;
import antifraud.domain.service.UserService;
import antifraud.persistence.repository.CustomUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final CustomUserRepository customUserRepository;
    private final PasswordEncoder encoder;

    @Override
    public Optional<User> registerUser(User userCredentials) {
        userCredentials.setPassword(encoder.encode(userCredentials.getPassword()));
        return customUserRepository.existsCustomUserByUsername(userCredentials.getUsername()) ?
                Optional.empty() :
                Optional.of(customUserRepository.save((CustomUser) userCredentials));
    }

    @Override
    public List<User> getUsers() {
        return customUserRepository.findAll().stream()
                .map(User.class::cast)
                .toList();
    }

    @Override
    public void deleteUser(String username) {
        CustomUser foundUser = customUserRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        customUserRepository.deleteById(foundUser.getId());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User foundUser = customUserRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new UserDetailsImpl(foundUser);
    }
}