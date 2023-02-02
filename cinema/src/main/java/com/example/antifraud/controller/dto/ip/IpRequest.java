package com.example.antifraud.controller.dto.ip;


import com.example.antifraud.validation.Ipv4;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IpRequest {

    @Ipv4
    String ip;

}
