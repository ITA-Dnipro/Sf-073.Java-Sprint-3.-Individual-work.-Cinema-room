package antifraud.domain.service.impl;

import antifraud.domain.model.CustomUser;
import antifraud.domain.model.User;
import antifraud.domain.model.UserPrincipal;
import antifraud.domain.model.enums.UserAccess;
import antifraud.domain.model.enums.UserRole;
import antifraud.domain.service.UserService;
import antifraud.exceptions.AccessViolationException;
import antifraud.exceptions.AlreadyProvidedException;
import antifraud.exceptions.ExistingAdministratorException;
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
        User foundUser = foundByUsername(username);
        customUserRepository.deleteById(foundUser.getId());
    }

    private User foundByUsername(String username) {
        return customUserRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Transactional
    @Override
    public User changeUserRole(User userWithRole) {
        User foundUser = foundByUsername(userWithRole.getUsername());
        roleCheckForCollision(userWithRole, foundUser);
        if (UserRole.ADMINISTRATOR.equals(userWithRole.getRole())) {
            throw new ExistingAdministratorException();
        }
        foundUser.setRole(userWithRole.getRole());
        return customUserRepository.save((CustomUser) foundUser);
    }

    private void roleCheckForCollision(User providedRole, User currentRole) {
        if (providedRole.getRole().equals(currentRole.getRole())) {
            throw new AlreadyProvidedException();
        }
    }

    @Transactional
    @Override
    public User grantAccess(User userWithAccessLevel) {
        User foundUser = foundByUsername(userWithAccessLevel.getUsername());
        roleCheckForAdmin(foundUser);
        foundUser.setAccess(userWithAccessLevel.getAccess());
        return customUserRepository.save((CustomUser) foundUser);
    }

    @Override
    public String retrieveRealUsername(String username) {
        User foundUser = foundByUsername(username);
        return foundUser.getUsername();
    }

    private void roleCheckForAdmin(User currentRole) {
        if (UserRole.ADMINISTRATOR.equals(currentRole.getRole())) {
            throw new AccessViolationException();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserPrincipal(foundByUsername(username));
    }
}