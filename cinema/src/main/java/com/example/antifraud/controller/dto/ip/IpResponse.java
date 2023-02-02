package com.example.antifraud.controller.dto.ip;

import com.example.antifraud.model.entities.IpEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class IpResponse {
    Long id;
    String ip;

    public IpResponse(IpEntity ipEntity) {
        id = ipEntity.getId();
        ip = ipEntity.getIp();
    }
}
