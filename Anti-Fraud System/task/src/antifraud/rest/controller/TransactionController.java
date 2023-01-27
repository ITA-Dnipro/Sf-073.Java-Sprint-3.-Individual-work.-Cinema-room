package antifraud.rest.controller;

import antifraud.domain.model.Transaction;
import antifraud.domain.service.IPService;
import antifraud.domain.service.TransactionService;
import antifraud.rest.dto.TransactionDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/antifraud")
public class TransactionController {
    private final TransactionService transactionService;
    private final IPService ipService;

    @PostMapping("/transaction")
    public TransactionDTO makeTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        Transaction deposit = transactionService.deposit(transactionDTO.toModel());
        return TransactionDTO.fromModel(deposit);
    }
}