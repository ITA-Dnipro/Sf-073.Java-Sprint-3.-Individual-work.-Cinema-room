package antifraud.service;

import antifraud.model.CleanCard;
import antifraud.model.Transaction;
import antifraud.model.TransactionFeedback;
import antifraud.model.TransactionResult;
import antifraud.model.TransactionResultResponse;
import antifraud.repository.CardRepository;
import antifraud.repository.CleanCardRepository;
import antifraud.repository.IpRepository;
import antifraud.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class TransactionService {
    @Autowired
    CardRepository cardRepository;
    @Autowired
    IpRepository ipRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    CleanCardRepository cleanCardRepository;
    int maxAllowed = 200;
    int maxManual = 1500;
    int distinctNumberAllowed = 2;
    int distinctNumberManual = 3;
    double multiplierForCurrentLimit = 0.8;
    double multiplierForCurrentAmount = 0.2;

    public static Set<String> getTransactionResultConstraints() {
        Set<String> values = new HashSet<>();
        for (TransactionResult c : TransactionResult.values()) {
            values.add(c.name());
        }
        return values;
    }

    public List<Transaction> transactions() {
        return transactionRepository.findAll();
    }

    public TransactionResultResponse transactionResult(Transaction transaction) {

        List<String> transactionInfo = new ArrayList<>();
        boolean prohibited = false;
        TransactionResult result;
        if (cardRepository.findByNumber(transaction.getNumber()).isPresent()) {
            prohibited = true;
            transactionInfo.add("card-number");
        } else {
            Optional<CleanCard> card = cleanCardRepository.findByNumber(transaction.getNumber());
            if (card.isPresent()) {
                maxAllowed = card.get().getMaxAllowed();
                maxManual = card.get().getMaxManual();
            } else {
                CleanCard newCard = new CleanCard();
                newCard.setNumber(transaction.getNumber());
                newCard.setMaxAllowed(maxAllowed);
                newCard.setMaxManual(maxManual);
                cleanCardRepository.save(newCard);
            }
        }
        if (ipRepository.findByIp(transaction.getIp()).isPresent()) {
            prohibited = true;
            transactionInfo.add("ip");
        }

        if (transaction.getAmount() <= maxAllowed) {
            result = TransactionResult.ALLOWED;
            transactionInfo.add("none");
        } else if (transaction.getAmount() <= maxManual) {
            result = TransactionResult.MANUAL_PROCESSING;
            if (!prohibited) {
                transactionInfo.add("amount");
            }
        } else {
            result = TransactionResult.PROHIBITED;
            transactionInfo.add("amount");
        }
        transactionRepository.save(transaction);
        List<Transaction> transactionHistory = transactionRepository.findByNumberAndDateBetween
                (transaction.getNumber(), transaction.getDate().minusHours(1), transaction.getDate());
        long distinctIps = transactionHistory.stream().map(Transaction::getIp).distinct().count();
        long distinctRegions = transactionHistory.stream().map(Transaction::getRegion).distinct().count();

        if (checkNumber(distinctRegions).equals(TransactionResult.PROHIBITED)) {
            result = TransactionResult.PROHIBITED;
            transactionInfo.removeIf(e -> e.contains("none"));
            transactionInfo.add("region-correlation");
        } else if (checkNumber(distinctRegions).equals(TransactionResult.MANUAL_PROCESSING)) {
            result = TransactionResult.MANUAL_PROCESSING;
            transactionInfo.removeIf(e -> e.contains("none"));
            transactionInfo.add("region-correlation");
        }

        if (checkNumber(distinctIps).equals(TransactionResult.PROHIBITED)) {
            result = TransactionResult.PROHIBITED;
            transactionInfo.removeIf(e -> e.contains("none"));
            transactionInfo.add("ip-correlation");
        } else if (checkNumber(distinctIps).equals(TransactionResult.MANUAL_PROCESSING)) {
            result = TransactionResult.MANUAL_PROCESSING;
            transactionInfo.removeIf(e -> e.contains("none"));
            transactionInfo.add("ip-correlation");
        }
        if (prohibited) {
            result = TransactionResult.PROHIBITED;
            Collections.sort(transactionInfo);
        }
        transaction.setResult(result.toString());
        transactionRepository.save(transaction);
        return new TransactionResultResponse(result, String.join(", ", transactionInfo));
    }

    private TransactionResult checkNumber(long number) {
        if (number <= distinctNumberAllowed) {
            return TransactionResult.ALLOWED;
        } else {
            if (number == distinctNumberManual) {
                return TransactionResult.MANUAL_PROCESSING;
            } else return TransactionResult.PROHIBITED;
        }
    }

    public Transaction updateFeedback(TransactionFeedback tsfb) {
        if (!getTransactionResultConstraints().contains(tsfb.getFeedback())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Transaction foundTransaction = transactionRepository.findById(tsfb.getTransactionId())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Optional<CleanCard> cleanCard = cleanCardRepository.findByNumber(foundTransaction.getNumber());
        CleanCard card = cleanCard.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        if (tsfb.getFeedback().equals(foundTransaction.getResult())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (!foundTransaction.getFeedback().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (tsfb.getFeedback().equals(TransactionResult.ALLOWED.toString())) {
            maxAllowed = increaseAllowed(foundTransaction);
            if (foundTransaction.getResult().equals(TransactionResult.PROHIBITED.toString())) {
                maxManual = increaseManual(foundTransaction);
            }
        }
        if (tsfb.getFeedback().equals(TransactionResult.
                MANUAL_PROCESSING.toString())) {
            if (foundTransaction.getResult().equals(TransactionResult.ALLOWED.toString())) {
                maxAllowed = (int) Math.ceil(multiplierForCurrentLimit * maxAllowed - multiplierForCurrentAmount * foundTransaction.getAmount());
            } else if (foundTransaction.getResult().equals(TransactionResult.PROHIBITED.toString())) {
                maxManual = increaseManual(foundTransaction);
            }
        }
        if (tsfb.getFeedback().equals(TransactionResult.PROHIBITED.toString())) {
            if (foundTransaction.getResult().equals(TransactionResult.ALLOWED.toString())) {
                maxAllowed = decreaseAllowed(foundTransaction);
                maxManual = decreaseManual(foundTransaction);
            } else if (foundTransaction.getResult().equals(TransactionResult.MANUAL_PROCESSING.toString())) {
                maxManual = decreaseManual(foundTransaction);
            }

        }
        card.setMaxAllowed(maxAllowed);
        card.setMaxManual(maxManual);
        cleanCardRepository.save(card);
        return transactionRepository.save(new Transaction(tsfb.getTransactionId(), foundTransaction.getAmount(),
                foundTransaction.getIp(), foundTransaction.getNumber(), foundTransaction.getRegion(),
                foundTransaction.getDate(),
                foundTransaction.getResult(), tsfb.getFeedback()));
    }

    private int decreaseManual(Transaction foundTransaction) {
        return (int) Math.ceil(multiplierForCurrentLimit * maxManual - multiplierForCurrentAmount * foundTransaction.getAmount());
    }

    private int decreaseAllowed(Transaction foundTransaction) {
        return (int) Math.ceil(multiplierForCurrentLimit * maxAllowed - multiplierForCurrentAmount - foundTransaction.getAmount());
    }

    private int increaseManual(Transaction foundTransaction) {
        return (int) Math.ceil(multiplierForCurrentLimit * maxManual + multiplierForCurrentAmount * foundTransaction.getAmount());
    }

    private int increaseAllowed(Transaction foundTransaction) {
        return (int) Math.ceil(multiplierForCurrentLimit * maxAllowed + multiplierForCurrentAmount * foundTransaction.getAmount());
    }

    public List<Transaction> historyForCard(String number) {
        if (!LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(number)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (transactionRepository.findAllByNumber(number).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return transactionRepository.findAllByNumber(number);

    }
}
