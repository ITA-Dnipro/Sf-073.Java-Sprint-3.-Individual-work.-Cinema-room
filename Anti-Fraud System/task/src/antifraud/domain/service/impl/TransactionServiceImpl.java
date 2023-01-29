package antifraud.domain.service.impl;

import antifraud.config.TransactionProperty;
import antifraud.domain.model.Transaction;
import antifraud.domain.model.enums.TransactionResult;
import antifraud.domain.service.TransactionService;
import antifraud.persistence.repository.StolenCardRepository;
import antifraud.persistence.repository.SuspiciousIPRepository;
import antifraud.persistence.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionProperty transactionProperty;
    private final TransactionRepository transactionRepository;
    private final SuspiciousIPRepository suspiciousIPRepository;
    private final StolenCardRepository stolenCardRepository;

    @Transactional
    @Override
    public Transaction processTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
        TransactionResult result = transactionResultByAmountMoney(transaction);
        String infoFromResult = infoFromTransactionResult(result);
        List<String> infoFromCorrelation = infoFromTransactionCorrelation(transaction);
        List<String> infoFromBlacklists = infoFromCardAndIpBlacklists(transaction);
        String info = gatheredInfo(infoFromResult, infoFromBlacklists, infoFromCorrelation);
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

    private List<String> infoFromTransactionCorrelation(Transaction transaction) {
        List<String> infoFromCorrelation = new ArrayList<>();
        List<Transaction> transactionsInLastHourOfTransactionHistory = transactionRepository.findByCardNumberAndDateTimeBetween(transaction.getCardNumber(),
                transaction.getDateTime().minusHours(1),
                transaction.getDateTime());
        long ipUniqueCount = transactionsInLastHourOfTransactionHistory.stream()
                .map(Transaction::getIpAddress)
                .distinct()
                .count();
        long regionUniqueCount = transactionsInLastHourOfTransactionHistory.stream()
                .map(Transaction::getWorldRegion)
                .distinct()
                .count();
        if (ipUniqueCount >= transactionProperty.correlation()) {
            infoFromCorrelation.add("ip-correlation");
        }
        if (regionUniqueCount >= transactionProperty.correlation()) {
            infoFromCorrelation.add("region-correlation");
        }
        changeResultBasedOnCorrelationInfo(ipUniqueCount, regionUniqueCount, transaction);
        return infoFromCorrelation;
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
        changeResultBasedOnBlacklistInfo(infoFromBlacklists.size(), transaction);
        return infoFromBlacklists;
    }

    /**
     * @param infoFromResult      information based on the result (type) of the transaction,
     *                            based on the deposit amount of money.
     * @param infoFromBlacklists  information based on the result from checking
     *                            the Suspicious IP and Stolen Card blacklists.
     * @param infoFromCorrelation information based on the correlation of transactions in the last hour
     *                            in the transaction history.
     * @return all the information gathered from the type of transaction, blacklists and transaction history's last hour.
     */
    private String gatheredInfo(String infoFromResult,
                                List<String> infoFromBlacklists,
                                List<String> infoFromCorrelation) {
        if (!infoFromBlacklists.isEmpty() && infoFromResult.equals("none")) {
            infoFromResult = "";
        }
        infoFromBlacklists.add(infoFromResult);
        infoFromBlacklists.addAll(infoFromCorrelation);
        return infoFromBlacklists.stream()
                .filter(s -> s.length() != 0)
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }

    private void changeResultBasedOnCorrelationInfo(long ipUniqueCount,
                                                    long regionUniqueCount,
                                                    Transaction transaction) {
        if (ipUniqueCount == transactionProperty.correlation() ||
                regionUniqueCount == transactionProperty.correlation()) {
            transaction.setTransactionResult(TransactionResult.MANUAL_PROCESSING);
        }
        if (ipUniqueCount > transactionProperty.correlation() ||
                regionUniqueCount > transactionProperty.correlation()) {
            transaction.setTransactionResult(TransactionResult.PROHIBITED);
        }
    }

    private void changeResultBasedOnBlacklistInfo(int blacklistSize, Transaction transaction) {
        if (blacklistSize > 0) {
            transaction.setTransactionResult(TransactionResult.PROHIBITED);
        }
    }
}