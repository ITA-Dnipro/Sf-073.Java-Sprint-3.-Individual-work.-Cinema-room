package antifraud.domain.service.impl;

import antifraud.domain.model.CustomUser;
import antifraud.domain.model.User;
import antifraud.domain.model.UserPrincipal;
import antifraud.domain.model.constants.UserAccess;
import antifraud.domain.model.constants.UserRole;
import antifraud.domain.service.UserService;
import antifraud.exceptions.AccessViolationException;
import antifraud.exceptions.AdministratorException;
import antifraud.exceptions.AlreadyProvidedException;
import antifraud.persistence.repository.CustomUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final CustomUserRepository customUserRepository;
    private final PasswordEncoder encoder;

    @Transactional
    @Override
    public Optional<User> registerUser(User userCredentials) {
        userCredentials.setPassword(encoder.encode(userCredentials.getPassword()));
        authorize(userCredentials);

        return customUserRepository.existsCustomUserByUsername(userCredentials.getUsername()) ?
                Optional.empty() :
                Optional.of(customUserRepository.save((CustomUser) userCredentials));
    }

    private void authorize(User userCredentials) {
        if (customUserRepository.count() == 0) {
            userCredentials.setRole(UserRole.ADMINISTRATOR);
            userCredentials.setAccess(UserAccess.UNLOCK);
        } else {
            userCredentials.setRole(UserRole.MERCHANT);
            userCredentials.setAccess(UserAccess.LOCK);
        }
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
        User foundUser = checkUsernameExistence(username);
        customUserRepository.deleteById(foundUser.getId());
    }

    private User checkUsernameExistence(String username) {
        return customUserRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public User changeUserRole(User userWithRole) {
        User foundUser = checkUsernameExistence(userWithRole.getUsername());
        roleCheck(userWithRole, foundUser);
        foundUser.setRole(userWithRole.getRole());
        return customUserRepository.save((CustomUser) foundUser);
    }

    private void roleCheck(User userWithRole, User foundUser) {
        if (userWithRole.getRole().equals(UserRole.ADMINISTRATOR)) {
            throw new AdministratorException();
        } else if (userWithRole.getRole().equals(foundUser.getRole())) {
            throw new AlreadyProvidedException();
        }
    }

    @Override
    public User grantAccess(User userWithAccessLevel) {
        User foundUser = checkUsernameExistence(userWithAccessLevel.getUsername());
        if (foundUser.getRole().equals(UserRole.ADMINISTRATOR)) {
            throw new AccessViolationException();
        }
        foundUser.setAccess(userWithAccessLevel.getAccess());
        return customUserRepository.save((CustomUser) foundUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserPrincipal(checkUsernameExistence(username));
    }
}