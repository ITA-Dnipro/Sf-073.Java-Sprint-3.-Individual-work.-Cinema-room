package antifraud.repository;

import antifraud.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByNumberAndDateBetween(String number, LocalDateTime dateStart, LocalDateTime dateEnd);
    List<Transaction> findAllByNumber(String number);
}
