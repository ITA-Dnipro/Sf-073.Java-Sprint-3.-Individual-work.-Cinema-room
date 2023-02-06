package antifraud.repositories;

import antifraud.domain.models.dao.IpEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IpRepository extends JpaRepository<IpEntity, Long> {

    Optional<IpEntity> findByIp(String ip);
}
