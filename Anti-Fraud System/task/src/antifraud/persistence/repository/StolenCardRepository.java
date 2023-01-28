package antifraud.persistence.repository;

import antifraud.domain.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StolenCardRepository extends JpaRepository<Card, Long> {

    boolean existsByNumber(String number);

    Optional<Card> findByNumber(String cardNumber);
}
