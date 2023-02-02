package com.example.antifraud.service.transaction;




import com.example.antifraud.controller.dto.transaction.Transaction;
import com.example.antifraud.controller.dto.transaction.TransactionResponse;

import java.util.Optional;

public interface TransactionService {
    Optional<TransactionResponse> postTransaction(Transaction transaction);
}
