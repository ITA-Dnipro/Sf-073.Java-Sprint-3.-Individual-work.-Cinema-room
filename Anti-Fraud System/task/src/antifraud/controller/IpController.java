package antifraud.controller;

import antifraud.model.Ip;
import antifraud.model.IpDeleteResponse;
import antifraud.model.IpResponse;
import antifraud.service.IpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IpController {
    @Autowired
    IpService ipService;


    @PostMapping("/api/antifraud/suspicious-ip")
    IpResponse saveIp(@RequestBody Ip ip) {
        return ipService.saveIp(ip);
    }

    @DeleteMapping("/api/antifraud/suspicious-ip/{ip}")
    IpDeleteResponse deleteIp(@PathVariable String ip) {
        return ipService.deleteByIp(ip);
    }

    @GetMapping("/api/antifraud/suspicious-ip")
    List<IpResponse> findAll() {
        return ipService.findAll();
    }
    //TODO

}
