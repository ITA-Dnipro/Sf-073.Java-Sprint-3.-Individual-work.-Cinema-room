package antifraud.services.contracts;

import antifraud.domain.models.dto.IpDto;
import antifraud.domain.models.request.IpSuspiciousCreateRequest;
import antifraud.domain.models.response.IpSuspiciousDeleteResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IpService {


    List<Object> getListOfSuspiciousIp();

    IpDto saveSuspiciousIp(IpSuspiciousCreateRequest ipSuspiciousCreateRequest);

    IpSuspiciousDeleteResponse deleteSuspiciousIpByIp(String ipSuspicious);

    boolean isIpExists(String ip);

}
