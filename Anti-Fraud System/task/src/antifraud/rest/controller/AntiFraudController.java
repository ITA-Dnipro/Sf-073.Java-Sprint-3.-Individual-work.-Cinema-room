package antifraud.rest.controller;

import antifraud.domain.model.IP;
import antifraud.domain.model.Transaction;
import antifraud.domain.service.IPService;
import antifraud.domain.service.TransactionService;
import antifraud.exceptions.ExistingIpException;
import antifraud.rest.dto.IpDTO;
import antifraud.rest.dto.TransactionDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/antifraud")
public class AntiFraudController {
    private final TransactionService transactionService;
    private final IPService ipService;

    @PostMapping("/transaction")
    public TransactionDTO makeTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        Transaction deposit = transactionService.deposit(transactionDTO.toModel());
        return TransactionDTO.fromModel(deposit);
    }

    @PostMapping("/suspicious-ip")
    public IpDTO saveSuspiciousIp(@Valid @RequestBody IpDTO ipDTO) {
        IP savedIP = ipService.saveSuspiciousAddress(ipDTO.toModel())
                .orElseThrow(() -> new ExistingIpException(HttpStatus.CONFLICT));
        return IpDTO.fromModel(savedIP);
    }
}