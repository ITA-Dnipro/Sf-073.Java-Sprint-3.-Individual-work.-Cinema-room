package antifraud.domain.service;


import antifraud.domain.model.IP;

import java.util.Optional;

public interface IPService {
    Optional<IP> saveSuspiciousAddress(IP address);
}