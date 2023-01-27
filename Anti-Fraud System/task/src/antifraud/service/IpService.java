package antifraud.service;

import antifraud.model.IpDTO;
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

@Service
public class IpService {
    final
    IpRepository ipRepo;

    public IpService(IpRepository ipRepo) {
        this.ipRepo = ipRepo;
    }

    public IpResponse saveIp(IpDTO ip){
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
        if(ipRepo.findByIp(ip).isPresent()){
            IpDTO ipDTO = ipRepo.findByIp(ip).get();
            ipRepo.deleteById(ipDTO.getId());
            return new IpDeleteResponse("IP " + ipDTO.getIp() + " successfully removed!");
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    public List<IpResponse> findAll(){
        List<IpResponse> ips = new ArrayList<>();
        for(var ip:ipRepo.findAll()){
            IpResponse ipResponse = new IpResponse();
            ipResponse.setId(ip.getId());
            ipResponse.setIp(ip.getIp());
            ips.add(ipResponse);
        }
        return ips;
    }
}
