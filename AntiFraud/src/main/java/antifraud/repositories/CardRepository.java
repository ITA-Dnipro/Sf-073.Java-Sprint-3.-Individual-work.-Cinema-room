package antifraud.repositories;

import antifraud.domain.models.dao.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<CardEntity, Long> {

    Optional<CardEntity> findByNumber(String cardNumber);
}
