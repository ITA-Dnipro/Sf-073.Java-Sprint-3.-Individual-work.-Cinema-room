package antifraud.domain.service;

import antifraud.domain.model.Transaction;

public interface TransactionService {

    Transaction processTransaction(Transaction transaction);

    void checkIfRegionExists(String region);
}