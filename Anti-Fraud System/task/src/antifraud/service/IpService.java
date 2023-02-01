package antifraud.service;

import antifraud.model.Ip;
import antifraud.model.IpDeleteResponse;
import antifraud.model.IpResponse;
import antifraud.repository.IpRepository;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IpService {
    @Autowired
    IpRepository ipRepo;

    public IpResponse saveIp(Ip ip){
        if(!InetAddressValidator.getInstance().isValid(ip.getIp())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        else if(ipRepo.findByIp(ip.getIp()).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        ipRepo.save(ip);
        return new IpResponse(ip.getId(),ip.getIp());
    }
   public IpDeleteResponse deleteByIp(String ip){
       if(!InetAddressValidator.getInstance().isValid(ip)){
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
       }
       Optional<Ip> byIp = ipRepo.findByIp(ip);
       if(byIp.isPresent()){
            Ip ipToBeDeleted = byIp.get();
            ipRepo.deleteById(ipToBeDeleted.getId());
            return new IpDeleteResponse("IP " + ipToBeDeleted.getIp() + " successfully removed!");
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    public List<IpResponse> findAll(){
        List<IpResponse> ips = new ArrayList<>();
        for(var ip:ipRepo.findAll()){
            ips.add(new IpResponse(ip.getId(), ip.getIp()));
        }
        return ips;
    }
}
