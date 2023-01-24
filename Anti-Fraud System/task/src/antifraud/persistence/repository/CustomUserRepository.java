package antifraud.persistence.repository;

import antifraud.domain.model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {
    boolean existsCustomUserByUsername(String username);

    CustomUser findByUsername(String username);

    CustomUser deleteByUsername(String username);
}