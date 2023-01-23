package antifraud.repository;

import antifraud.model.UnauthorisedUser;
import antifraud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UnauthorisedUser, Long> {
    Optional<UnauthorisedUser> findByUsername(String username);
}
