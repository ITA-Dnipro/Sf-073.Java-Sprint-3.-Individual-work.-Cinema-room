package antifraud.controllers;

import antifraud.domain.models.dto.IpDto;
import antifraud.domain.models.request.IpSuspiciousCreateRequest;
import antifraud.domain.models.response.IpSuspiciousCreateResponse;
import antifraud.domain.models.response.IpSuspiciousDeleteResponse;
import antifraud.services.contracts.IpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@PreAuthorize("hasRole('SUPPORT')")
@RequestMapping("api/antifraud")
@RestController
public class IpController {

    @Autowired
    IpService ipService;
    @PostMapping(value = "/suspicious-ip")
    ResponseEntity<IpSuspiciousCreateResponse> saveSuspiciousIp(@Valid @RequestBody IpSuspiciousCreateRequest ipSuspiciousCreateRequest) {
        IpDto ipDto = ipService.saveSuspiciousIp(ipSuspiciousCreateRequest);
        return new ResponseEntity<>(new IpSuspiciousCreateResponse(ipDto), HttpStatus.OK);
    }
    @GetMapping(value = "/suspicious-ip")
    ResponseEntity<List<Object>> getListOfSuspiciousIp() {
        return new ResponseEntity<>(ipService.getListOfSuspiciousIp(),HttpStatus.OK);
    }

    @DeleteMapping(value = "/suspicious-ip/{ip}")
    ResponseEntity<IpSuspiciousDeleteResponse> deleteSuspiciousIp(@Valid @PathVariable("ip") antifraunullmain.models.request.IpSuspiciousDeleteRequest ipSuspiciousDeleteRequest) {
        ipService.deleteSuspiciousIpByIp(ipSuspiciousDeleteRequest.getIpSuspicious());
        return new ResponseEntity<>(new IpSuspiciousDeleteResponse(ipSuspiciousDeleteRequest.getIpSuspicious()), HttpStatus.OK);
    }
}
