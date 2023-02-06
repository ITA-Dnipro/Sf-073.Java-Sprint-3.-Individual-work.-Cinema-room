package antifraud.services;

import antifraud.domain.models.dao.IpEntity;
import antifraud.domain.models.dto.IpDto;
import antifraud.domain.models.request.IpSuspiciousCreateRequest;
import antifraud.domain.models.response.IpSuspiciousCreateResponse;
import antifraud.domain.models.response.IpSuspiciousDeleteResponse;
import antifraud.repositories.IpRepository;
import antifraud.services.contracts.IpService;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IpServiceImpl implements IpService {

    @Autowired
    IpRepository ipRepository;

    @Override
    public List<Object> getListOfSuspiciousIp() {
        List<IpEntity> ipEntityList = ipRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return ipEntityList
                .stream().map(entity -> new IpSuspiciousCreateResponse(new IpDto(entity.getId(),
                        entity.getIp())))
                .collect(Collectors.toList());
    }

    @Override
    public IpDto saveSuspiciousIp(IpSuspiciousCreateRequest ipSuspiciousCreateRequest) {
        InetAddressValidator ipValidator = InetAddressValidator.getInstance();
        if (!ipValidator.isValidInet4Address(ipSuspiciousCreateRequest.getIp())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (isIpExists(ipSuspiciousCreateRequest.getIp())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        IpEntity ipEntity = new IpEntity();
        ipEntity.setIp(ipSuspiciousCreateRequest.getIp());
        IpEntity ipEntityUpdatedRecord = ipRepository.save(ipEntity);
        System.out.println(ipEntityUpdatedRecord.getId());
        System.out.println(ipEntityUpdatedRecord.getIp());
        return mapIpEntityToIpDto(ipEntityUpdatedRecord);
    }

    @Override
    public IpSuspiciousDeleteResponse deleteSuspiciousIpByIp(String ipSuspicious) {
        InetAddressValidator ipValidator = InetAddressValidator.getInstance();
        if (!ipValidator.isValidInet4Address(ipSuspicious)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Optional<IpEntity> ipEntity = ipRepository.findByIp(ipSuspicious);
        if (ipEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        ipRepository.delete(ipEntity.get());
        return new IpSuspiciousDeleteResponse(ipEntity.get().getIp());
    }

    @Override
    public boolean isIpExists(String ip) {
        return ipRepository.findByIp(ip).isPresent();
    }

    private IpDto mapIpEntityToIpDto(IpEntity ipEntity) {
        IpDto ipDto = new IpDto();
        ipDto.setId(ipEntity.getId());
        ipDto.setIp(ipEntity.getIp());
        return ipDto;
    }
}
