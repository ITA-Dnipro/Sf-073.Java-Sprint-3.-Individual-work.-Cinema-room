package antifraud.persistence.repository;

import antifraud.domain.model.CustomUser;
import antifraud.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {
    boolean existsCustomUserByUsername(String username);

    Optional<User> findByUsernameIgnoreCase(String username);

}