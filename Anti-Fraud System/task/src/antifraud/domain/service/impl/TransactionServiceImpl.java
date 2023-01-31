package antifraud.domain.service.impl;

import antifraud.config.transaction.TransactionProperty;
import antifraud.domain.model.Transaction;
import antifraud.domain.model.enums.TransactionResult;
import antifraud.domain.service.TransactionService;
import antifraud.exceptions.ExistingFeedbackException;
import antifraud.exceptions.SameResulException;
import antifraud.exceptions.TransactionsNotFoundException;
import antifraud.persistence.repository.StolenCardRepository;
import antifraud.persistence.repository.SuspiciousIPRepository;
import antifraud.persistence.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
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
        TransactionResult resultByAmountMoney = transactionResultByAmountMoney(transaction);
        String infoFromInitialResult = infoFromInitialTransactionResult(resultByAmountMoney);
        List<Transaction> transactionsInLastHourOfTransactionHistory =
                transactionRepository.findByCardNumberAndDateTimeBetween(transaction.getCardNumber(),
                        transaction.getDateTime().minusHours(1),
                        transaction.getDateTime());
        long ipUniqueCount =
                correlationCount(transactionsInLastHourOfTransactionHistory,
                        Transaction::getIpAddress);
        long regionUniqueCount =
                correlationCount(transactionsInLastHourOfTransactionHistory,
                        r -> r.getWorldRegion().name());
        List<String> infoFromCorrelation =
                infoFromTransactionCorrelationCount(ipUniqueCount, regionUniqueCount);
        List<String> infoFromBlacklists = infoFromCardAndIpBlacklists(transaction);

        TransactionResult resultBasedOnInfo =
                resultBasedOnInfoNumbers(ipUniqueCount,
                        regionUniqueCount,
                        infoFromBlacklists.size(),
                        resultByAmountMoney);
        if (!resultBasedOnInfo.equals(resultByAmountMoney)) {
            infoFromInitialResult = "";
        }
        transaction.setTransactionResult(resultBasedOnInfo);
        String allInfo = gatheredInfo(infoFromInitialResult, infoFromBlacklists, infoFromCorrelation);
        transaction.setTransactionInfo(allInfo);
        return transactionRepository.save(transaction);
    }

    private TransactionResult transactionResultByAmountMoney(Transaction transaction) {
        Long money = transaction.getMoney();
        if (money <= transactionProperty.getAllowed()) {
            return TransactionResult.ALLOWED;
        } else if (money <= transactionProperty.getManualProcessing()) {
            return TransactionResult.MANUAL_PROCESSING;
        } else {
            return TransactionResult.PROHIBITED;
        }
    }

    private String infoFromInitialTransactionResult(TransactionResult transactionResult) {
        return TransactionResult.ALLOWED.equals(transactionResult) ?
                "none" : "amount";
    }

    private long correlationCount(List<Transaction> transactions,
                                  Function<Transaction, String> transactionField) {
        return transactions.stream()
                .map(transactionField)
                .distinct()
                .count();
    }

    private List<String> infoFromTransactionCorrelationCount(Long ipUniqueCount, Long regionUniqueCount) {
        List<String> infoFromCorrelation = new ArrayList<>();
        if (ipUniqueCount >= transactionProperty.getCorrelation()) {
            infoFromCorrelation.add("ip-correlation");
        }
        if (regionUniqueCount >= transactionProperty.getCorrelation()) {
            infoFromCorrelation.add("region-correlation");
        }
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
        return infoFromBlacklists;
    }

    /**
     * @param ipUniqueCount     number of unique IPs from transactions happened in the last hour
     *                          in the transaction history.
     * @param regionUniqueCount number of unique regions from transactions happened in the last hour
     *                          in the transaction history.
     * @param blacklistSize     sum of Suspicious IP and Stolen Card blacklists' size.
     * @param result            Transaction with initial transaction's result based on the deposit money.
     * @return transaction result based on the information numbers. if nothing is changed based on numbers,
     * method returns initial transaction result.
     */
    private TransactionResult resultBasedOnInfoNumbers(long ipUniqueCount,
                                                       long regionUniqueCount,
                                                       int blacklistSize,
                                                       TransactionResult result) {
        if ((ipUniqueCount == transactionProperty.getCorrelation() ||
                regionUniqueCount == transactionProperty.getCorrelation()) &&
                !TransactionResult.PROHIBITED.equals(result)) {
            result = TransactionResult.MANUAL_PROCESSING;
        }
        if (ipUniqueCount > transactionProperty.getCorrelation() ||
                regionUniqueCount > transactionProperty.getCorrelation()) {
            result = TransactionResult.PROHIBITED;
        }
        if (blacklistSize > 0) {
            result = TransactionResult.PROHIBITED;
        }
        return result;
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

    @Override
    public Transaction giveFeedback(Transaction feedback) {
        Transaction foundTransaction = transactionRepository.findById(feedback.getId())
                .orElseThrow(TransactionsNotFoundException::new);
        if (transactionRepository.existsByFeedbackAndFeedbackNotNull(foundTransaction.getFeedback())) {
            throw new ExistingFeedbackException(HttpStatus.CONFLICT);
        }
        feedbackCheckForCollision(feedback, foundTransaction);
        foundTransaction.setFeedback(feedback.getFeedback());
        changeLimitsOfFraudDetectionAlgorithm(feedback, foundTransaction);
        return transactionRepository.save(foundTransaction);
    }

    /**
     * Checks if the provided feedback is the same as the current transaction result.
     * If it is, method throws an exception.
     *
     * @param feedback      Transaction with given feedback.
     * @param currentResult Transaction with current transaction result.
     */
    private void feedbackCheckForCollision(Transaction feedback, Transaction currentResult) {
        if (feedback.getFeedback().equals(currentResult.getTransactionResult())) {
            throw new SameResulException();
        }
    }

    /**
     * Change the limits of ALLOWED and MANUAL_PROCESSING values.
     *
     * @param feedback      Transaction with given feedback.
     * @param currentResult Transaction with current transaction result.
     */
    private void changeLimitsOfFraudDetectionAlgorithm(Transaction feedback,
                                                       Transaction currentResult) {

        if (TransactionResult.ALLOWED.equals(feedback.getFeedback())) {
            if (TransactionResult.MANUAL_PROCESSING.equals(currentResult.getTransactionResult())) {
                increaseLimit(transactionProperty.getAllowed(), currentResult.getMoney(),
                        TransactionResult.ALLOWED);
            } else {
                decreaseLimit(transactionProperty.getAllowed(), currentResult.getMoney(),
                        TransactionResult.ALLOWED);
                decreaseLimit(transactionProperty.getManualProcessing(), currentResult.getMoney(),
                        TransactionResult.MANUAL_PROCESSING);
            }
        }
        if (TransactionResult.MANUAL_PROCESSING.equals((feedback.getFeedback()))) {
            if (TransactionResult.ALLOWED.equals(currentResult.getTransactionResult())) {
                decreaseLimit(transactionProperty.getAllowed(), currentResult.getMoney(),
                        TransactionResult.ALLOWED);
            } else {
                increaseLimit(transactionProperty.getManualProcessing(), currentResult.getMoney(),
                        TransactionResult.MANUAL_PROCESSING);
            }
        }
        if (TransactionResult.PROHIBITED.equals(feedback.getFeedback())) {
            if (TransactionResult.ALLOWED.equals(currentResult.getTransactionResult())) {
                decreaseLimit(transactionProperty.getAllowed(), currentResult.getMoney(),
                        TransactionResult.ALLOWED);
                decreaseLimit(transactionProperty.getManualProcessing(), currentResult.getMoney(),
                        TransactionResult.MANUAL_PROCESSING);
            } else {
                decreaseLimit(transactionProperty.getManualProcessing(), currentResult.getMoney(),
                        TransactionResult.MANUAL_PROCESSING);
            }
        }
    }

    /**
     * Increase the limit values of ALLOWED or MANUAL_PROCESSING.
     *
     * @param currentLimit of fraud-detection algorithm 'allowed' or 'manual_processing' value.
     * @param money        amount of current deposit.
     * @param result       transaction result(type) which limit will be increased.
     */
    private void increaseLimit(int currentLimit, Long money, TransactionResult result) {
        int newLimit = (int) Math.ceil(transactionProperty.getCurrentLimitFactor() * currentLimit +
                transactionProperty.getCurrentDepositFactor() * money);
        if (TransactionResult.ALLOWED.equals(result)) {
            transactionProperty.setAllowed(newLimit);
        } else {
            transactionProperty.setManualProcessing(newLimit);
        }
    }

    /**
     * Decrease the limit values of ALLOWED or MANUAL_PROCESSING.
     *
     * @param currentLimit of fraud-detection algorithm 'allowed' or 'manual_processing' value.
     * @param money        amount of current deposit.
     * @param result       transaction result(type) which limit will be decreased.
     */
    private void decreaseLimit(int currentLimit, Long money, TransactionResult result) {
        int newLimit = (int) Math.ceil(transactionProperty.getCurrentLimitFactor() * currentLimit -
                transactionProperty.getCurrentDepositFactor() * money);
        if (TransactionResult.ALLOWED.equals(result)) {
            transactionProperty.setAllowed(newLimit);
        } else {
            transactionProperty.setManualProcessing(newLimit);
        }
    }

    @Override
    public List<Transaction> showTransactionHistory() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> showTransactionHistoryForSpecificCardNumber(String cardNumber) {
        return transactionRepository.findTransactionByCardNumber(cardNumber)
                .orElseThrow(TransactionsNotFoundException::new);
    }
}