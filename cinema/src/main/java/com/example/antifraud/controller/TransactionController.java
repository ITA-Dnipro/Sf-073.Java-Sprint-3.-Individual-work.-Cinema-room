package com.example.antifraud.controller;


import com.example.antifraud.controller.dto.transaction.Transaction;
import com.example.antifraud.controller.dto.transaction.TransactionResponse;
import com.example.antifraud.service.transaction.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Validated
public class TransactionController {
    TransactionService transService;
    @PostMapping("/api/antifraud/transaction")
    public TransactionResponse transact(@RequestBody @Valid Transaction transaction) {
        return transService.postTransaction(transaction)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

}
