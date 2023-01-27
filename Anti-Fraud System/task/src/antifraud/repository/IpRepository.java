package antifraud.repository;

import antifraud.model.IpDTO;
import antifraud.model.IpResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface IpRepository extends JpaRepository<IpDTO, Long>{
    Optional<IpDTO> findByIp(String ip);

}
