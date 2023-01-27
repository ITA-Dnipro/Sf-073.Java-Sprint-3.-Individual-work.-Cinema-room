package antifraud.persistence.repository;

import antifraud.domain.model.IP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPRepository extends JpaRepository<IP, Long> {

    boolean existsByIpAddress(String ipAddress);
}