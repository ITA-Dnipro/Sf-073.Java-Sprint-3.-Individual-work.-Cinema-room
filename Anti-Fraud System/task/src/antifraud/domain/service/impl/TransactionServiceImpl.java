package antifraud.domain.service.impl;

import antifraud.config.TransactionProperty;
import antifraud.domain.model.Transaction;
import antifraud.domain.model.enums.TransactionResult;
import antifraud.domain.service.TransactionService;
import antifraud.persistence.repository.StolenCardRepository;
import antifraud.persistence.repository.SuspiciousIPRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionProperty transactionProperty;
    private final SuspiciousIPRepository suspiciousIPRepository;
    private final StolenCardRepository stolenCardRepository;

    @Override
    public Transaction processTransaction(Transaction transaction) {
        TransactionResult transactionResult = transactionTypeByAmountMoney(transaction);
        transaction.setTransactionResult(transactionResult);
        List<String> infoFromCards = infoFromCardAndIpBlacklists(transaction);
        if (!infoFromCards.isEmpty()) {
            transaction.setTransactionResult(TransactionResult.PROHIBITED);
        }
        String infoFromResult = infoFromResultAndInputMoney(transaction.getTransactionResult(),
                transaction.getMoney());
        infoFromCards.add(infoFromResult);
        Collections.sort(infoFromCards);
        String calculatedInfo = infoFromCards.stream()
                .filter(s -> s.length() != 0)
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
        transaction.setTransactionInfo(calculatedInfo);
        return transaction;
    }

    private TransactionResult transactionTypeByAmountMoney(Transaction transaction) {
        Long money = transaction.getMoney();
        if (money <= transactionProperty.allowed()) {
            return TransactionResult.ALLOWED;
        } else if (money <= transactionProperty.manualProcessing()) {
            return TransactionResult.MANUAL_PROCESSING;
        } else {
            return TransactionResult.PROHIBITED;
        }
    }

    private List<String> infoFromCardAndIpBlacklists(Transaction transaction) {
        List<String> infoFromBlacklists = new ArrayList<>();
        boolean ipBlacklisted = checkIpAddressBlacklist(transaction.getIpAddress());
        boolean cardBlacklisted = checkCardNumberBlacklist(transaction.getCardNumber());
        if (ipBlacklisted) {
            infoFromBlacklists.add("ip");
        }
        if (cardBlacklisted) {
            infoFromBlacklists.add("card-number");
        }
        return infoFromBlacklists;
    }

    private String infoFromResultAndInputMoney(TransactionResult transactionResult, Long money) {
        String infoResult = "";
        if (TransactionResult.ALLOWED.equals(transactionResult)) {
            infoResult = "none";
        } else if (TransactionResult.MANUAL_PROCESSING.equals(transactionResult)) {
            infoResult = "amount";
        } else if (TransactionResult.PROHIBITED.equals(transactionResult) &&
                money > 1500) {
            infoResult = "amount";
        }
        return infoResult;
    }

    private boolean checkIpAddressBlacklist(String ipAddress) {
        return suspiciousIPRepository.existsByIpAddress(ipAddress);
    }

    private boolean checkCardNumberBlacklist(String cardNumber) {
        return stolenCardRepository.existsByNumber(cardNumber);
    }
}