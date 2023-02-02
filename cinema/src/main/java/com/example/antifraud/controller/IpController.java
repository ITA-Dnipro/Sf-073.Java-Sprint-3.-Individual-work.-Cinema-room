package com.example.antifraud.controller;


import com.example.antifraud.controller.dto.ip.DeleteIpResponse;
import com.example.antifraud.service.ip.IpService;
import com.example.antifraud.validation.Ipv4;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;

import com.example.antifraud.model.entities.IpEntity;
import com.example.antifraud.controller.dto.ip.IpResponse;


@RestController
@RequestMapping("/api/antifraud")
@Validated
@AllArgsConstructor
public class IpController {
    IpService ipService;

    @PostMapping("/suspicious-ip")
    public IpResponse createIp(@Valid @RequestBody IpEntity ipEntity) {
        return ipService.postIp(ipEntity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT));
    }

    @GetMapping("/suspicious-ip")
    public List<IpResponse> getAllIps() {
        return ipService.getAllIps();
    }

    @DeleteMapping("/suspicious-ip/{ip}")
    @ResponseStatus(HttpStatus.OK)
    public DeleteIpResponse deleteUser(@PathVariable("ip") @Ipv4 String ip) {

        if (ipService.delete(ip)) {
            String msg = "IP " + ip + " successfully removed!";
            return new DeleteIpResponse(msg);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>(
                "not valid due to validation error: " +
                        e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
