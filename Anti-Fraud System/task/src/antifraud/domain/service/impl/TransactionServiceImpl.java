package antifraud.domain.service.impl;

import antifraud.config.TransactionProperty;
import antifraud.domain.model.Transaction;
import antifraud.domain.model.enums.TransactionResult;
import antifraud.domain.model.enums.WorldRegion;
import antifraud.domain.service.TransactionService;
import antifraud.exceptions.NonExistentRegionException;
import antifraud.persistence.repository.StolenCardRepository;
import antifraud.persistence.repository.SuspiciousIPRepository;
import antifraud.persistence.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionProperty transactionProperty;
    private final TransactionRepository transactionRepository;
    private final SuspiciousIPRepository suspiciousIPRepository;
    private final StolenCardRepository stolenCardRepository;

    @Override
    public Transaction processTransaction(Transaction transaction) {
        TransactionResult result = transactionResultByAmountMoney(transaction);
        String infoFromResult = infoFromTransactionResult(result);
        List<String> infoFromCards = infoFromCardAndIpBlacklists(transaction);
        String info = calculatedInfo(infoFromResult, infoFromCards);
        if (!infoFromCards.isEmpty()) {
            result = TransactionResult.PROHIBITED;
        }
        transaction.setTransactionResult(result);
        transaction.setTransactionInfo(info);
        return transactionRepository.save(transaction);
    }

    private TransactionResult transactionResultByAmountMoney(Transaction transaction) {
        Long money = transaction.getMoney();
        if (money <= transactionProperty.allowed()) {
            return TransactionResult.ALLOWED;
        } else if (money <= transactionProperty.manualProcessing()) {
            return TransactionResult.MANUAL_PROCESSING;
        } else {
            return TransactionResult.PROHIBITED;
        }
    }

    private String infoFromTransactionResult(TransactionResult transactionResult) {
        return TransactionResult.ALLOWED.equals(transactionResult) ?
                "none" : "amount";
    }

    private List<String> infoFromCardAndIpBlacklists(Transaction transaction) {
        List<String> infoFromBlacklists = new ArrayList<>();
        boolean ipBlacklisted = suspiciousIPRepository.existsByIpAddress(transaction.getIpAddress());
        boolean cardBlacklisted = stolenCardRepository.existsByNumber(transaction.getCardNumber());
        if (ipBlacklisted) {
            infoFromBlacklists.add("ip");
        }
        if (cardBlacklisted) {
            infoFromBlacklists.add("card-number");
        }
        return infoFromBlacklists;
    }

    private String calculatedInfo(String infoFromResult, List<String> infoFromCards) {
        if (!infoFromCards.isEmpty() && infoFromResult.equals("none")) {
            infoFromResult = "";
        }
        infoFromCards.add(infoFromResult);
        return infoFromCards.stream()
                .filter(s -> s.length() != 0)
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }

    @Override
    public void checkIfRegionExists(String region) {
        boolean doesExist = Arrays.stream(WorldRegion.values())
                .anyMatch(r -> r.name().equals(region));
        if (!doesExist) {
            throw new NonExistentRegionException();
        }
    }
}