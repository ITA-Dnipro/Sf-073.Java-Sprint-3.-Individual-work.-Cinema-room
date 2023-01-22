package antifraud.domain.service;

import antifraud.domain.model.Transaction;

public interface TransactionService {
    Transaction deposit(Transaction transaction);
}