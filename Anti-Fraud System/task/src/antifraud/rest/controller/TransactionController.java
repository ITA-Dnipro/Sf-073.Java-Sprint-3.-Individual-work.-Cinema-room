package antifraud.rest.controller;

import antifraud.domain.model.Transaction;
import antifraud.domain.service.TransactionService;
import antifraud.rest.dto.TransactionDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/api/antifraud/transaction")
    TransactionDTO makeTransaction(@RequestBody @Valid TransactionDTO transactionDTO) {
        Transaction deposit = transactionService.deposit(transactionDTO.toModel());
        return TransactionDTO.fromModel(deposit);
    }
}