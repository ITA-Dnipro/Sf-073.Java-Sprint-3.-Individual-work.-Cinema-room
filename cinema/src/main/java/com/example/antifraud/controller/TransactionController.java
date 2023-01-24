package com.example.antifraud.controller;

import com.example.antifraud.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.antifraud.model.Transaction;
import com.example.antifraud.model.TransactionResponse;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Validated
public class TransactionController {

    private final TransactionService transService;

    @PostMapping("/api/antifraud/transaction")
    public TransactionResponse transact(@RequestBody @Valid Transaction transaction) {
        return new TransactionResponse(transService.transfer(transaction.getAmount()));
    }
}
