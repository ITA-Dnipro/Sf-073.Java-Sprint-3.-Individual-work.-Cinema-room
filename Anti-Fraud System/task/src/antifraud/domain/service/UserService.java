package antifraud.domain.service;

import antifraud.domain.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    Optional<User> registerUser(User userCredentials);

    List<User> getUsers();

    void deleteUser(String username);

    User changeUserRole(User userWithRole);

    User grantAccess(User userWithAccessLevel);

    void checkIfRoleExists(String role);
}