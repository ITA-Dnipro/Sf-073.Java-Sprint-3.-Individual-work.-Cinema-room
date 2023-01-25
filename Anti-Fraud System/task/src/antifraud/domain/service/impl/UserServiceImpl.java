package antifraud.domain.service.impl;

import antifraud.domain.model.CustomUser;
import antifraud.domain.model.User;
import antifraud.domain.model.UserPrincipal;
import antifraud.domain.service.UserService;
import antifraud.persistence.repository.CustomUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final CustomUserRepository customUserRepository;
    private final PasswordEncoder encoder;

    @Transactional
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

    @Transactional
    @Override
    public void deleteUser(String username) {
        CustomUser foundUser = customUserRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        customUserRepository.deleteById(foundUser.getId());
    }

    @Override
    public User changeUserRole(User userWithRole) {
        CustomUser foundUser = customUserRepository.findByUsernameIgnoreCase(userWithRole.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(userWithRole.getUsername()));
        foundUser.setRole(userWithRole.getRole());
        return customUserRepository.save(foundUser);
    }

    @Override
    public User grantAccess(User accessLevel) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User foundUser = customUserRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new UserPrincipal(foundUser);
    }
}