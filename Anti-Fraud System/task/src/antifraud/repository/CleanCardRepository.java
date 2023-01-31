package antifraud.repository;

import antifraud.model.CardDTO;
import antifraud.model.CleanCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CleanCardRepository extends JpaRepository<CleanCard, Long> {

    Optional<CleanCard> findByNumber(String cardNumber);
}
