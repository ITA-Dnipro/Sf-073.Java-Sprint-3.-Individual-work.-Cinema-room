package com.example.antifraud.service.ip;


import com.example.antifraud.controller.dto.ip.IpResponse;
import com.example.antifraud.model.entities.IpEntity;
import com.example.antifraud.repository.IpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IpServiceImpl implements IpService {

    @Autowired
    IpRepository ipRepository;

    @Override
    public Optional<IpResponse> postIp(IpEntity ipEntity) {
        if (ipRepository.existsByIp(ipEntity.getIp())) {
            return Optional.empty();
        }

        ipEntity.setIp(ipEntity.getIp());
        ipRepository.save(ipEntity);


        return Optional.of( new IpResponse(ipEntity)) ;
    }

    @Override
    @Transactional
    public boolean delete(String ip) {
        int result = ipRepository.deleteByIp(ip);
        return result == 1;
    }

    @Override
    @Transactional
    public List<IpResponse> getAllIps() {
        List<IpResponse> ipList = new ArrayList<>();
        ipRepository.findAll(Sort.by("id"))
                .forEach(ip -> ipList.add(
                        new IpResponse(ip)
                ));
        return ipList;
    }
}
