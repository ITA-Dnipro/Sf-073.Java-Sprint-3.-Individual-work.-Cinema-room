package antifraud.domain.service.impl;

import antifraud.domain.model.IP;
import antifraud.domain.service.IPService;
import antifraud.exceptions.IpNotFoundException;
import antifraud.persistence.repository.IPRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class IPServiceImpl implements IPService {
    private final IPRepository ipRepository;

    @Transactional
    @Override
    public Optional<IP> saveSuspiciousAddress(IP address) {
       return ipRepository.existsByIpAddress(address.getIpAddress()) ?
               Optional.empty() :
               Optional.of(ipRepository.save(address));
    }

    @Transactional
    @Override
    public void removeIpAddress(String ipAddress) {
        IP foundIpAddress = ipRepository.findByIpAddress(ipAddress)
                .orElseThrow(() -> new IpNotFoundException(ipAddress));
        ipRepository.deleteById(foundIpAddress.getId());
    }

    @Override
    public List<IP> showIpAddresses() {
        return ipRepository.findAll();
    }
}