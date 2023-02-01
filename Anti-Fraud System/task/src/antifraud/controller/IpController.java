package antifraud.controller;

import antifraud.model.Ip;
import antifraud.model.IpDeleteResponse;
import antifraud.model.IpResponse;
import antifraud.service.IpService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IpController {
    final
    IpService ipService;

    public IpController(IpService ipService) {
        this.ipService = ipService;
    }

    @PostMapping("/api/antifraud/suspicious-ip")
    IpResponse saveIp(@RequestBody Ip ip){
    return ipService.saveIp(ip);
    }
    @DeleteMapping("/api/antifraud/suspicious-ip/{ip}")
    IpDeleteResponse deleteIp(@PathVariable String ip){
        return ipService.deleteByIp(ip);
    }
    @GetMapping("/api/antifraud/suspicious-ip")
    List<IpResponse> findAll(){
        return ipService.findAll();
    }
    //TODO
    
}
