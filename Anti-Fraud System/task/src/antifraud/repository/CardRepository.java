package antifraud.repository;

import antifraud.model.CardDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<CardDTO, Long> {

    Optional<CardDTO> findByNumber(String cardNumber);
}
