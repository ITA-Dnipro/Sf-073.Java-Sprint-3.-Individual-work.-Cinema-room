package antifraud.domain.service.impl;

import antifraud.domain.model.Transaction;
import antifraud.domain.model.TransactionResult;
import antifraud.domain.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final Environment env;

    @Override
    public Transaction deposit(Transaction transaction) {
        long allowed = Long.parseLong(env.getRequiredProperty("ALLOWED"));
        long manualProcessing = Long.parseLong(env.getRequiredProperty("MANUAL_PROCESSING"));
        Long money = transaction.getMoney();
        if (money <= allowed) {
            transaction.setTransactionResult(TransactionResult.ALLOWED);
            return transaction;
        } else if (money <= manualProcessing) {
            transaction.setTransactionResult(TransactionResult.MANUAL_PROCESSING);
            return transaction;
        } else {
            transaction.setTransactionResult(TransactionResult.PROHIBITED);
            return transaction;
        }
    }

}
