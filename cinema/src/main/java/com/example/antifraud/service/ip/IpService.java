package com.example.antifraud.service.ip;



import com.example.antifraud.controller.dto.ip.IpResponse;
import com.example.antifraud.model.entities.IpEntity;

import java.util.List;
import java.util.Optional;

public interface IpService {
    Optional<IpResponse> postIp(IpEntity ipEntity);
    boolean delete(String ip);
    List<IpResponse> getAllIps();
}
