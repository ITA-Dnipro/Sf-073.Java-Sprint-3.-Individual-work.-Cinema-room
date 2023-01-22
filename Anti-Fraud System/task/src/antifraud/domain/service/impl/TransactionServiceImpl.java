package antifraud.domain.service.impl;

import antifraud.config.TransactionProperty;
import antifraud.domain.model.Transaction;
import antifraud.domain.model.TransactionResult;
import antifraud.domain.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionProperty transactionProperty;

    @Override
    public Transaction deposit(Transaction transaction) {
        Long money = transaction.getMoney();
        if (money <= transactionProperty.allowed()) {
            transaction.setTransactionResult(TransactionResult.ALLOWED);
            return transaction;
        } else if (money <= transactionProperty.manualProcessing()) {
            transaction.setTransactionResult(TransactionResult.MANUAL_PROCESSING);
            return transaction;
        } else {
            transaction.setTransactionResult(TransactionResult.PROHIBITED);
            return transaction;
        }
    }

}
