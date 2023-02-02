package com.example.antifraud.service.transaction;

import com.example.antifraud.configuration.TransactionProperties;
import com.example.antifraud.repository.IpRepository;
import com.example.antifraud.repository.StolenCreditCard;
import com.example.antifraud.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.example.antifraud.controller.dto.transaction.Transaction;
import com.example.antifraud.controller.dto.transaction.TransactionResponse;
import com.example.antifraud.model.entities.TransactionEntity;
import com.example.antifraud.model.enums.Region;

@Service
@RequiredArgsConstructor
public class TransactionServiceIMPL implements TransactionService {

    final TransactionProperties properties;
    private static final int ALLOWED_AMOUNT = 200;
    private static final int MANUAL_PROCESSING_AMOUNT = 1500;

    @Autowired
    IpRepository ipRepository;
    @Autowired
    StolenCreditCard stolenCreditCard;

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public Optional<TransactionResponse> postTransaction(Transaction transaction) {
        TransactionEntity transactionEntity = new TransactionEntity();

        transactionEntity.setAmount(transaction.getAmount());
        transactionEntity.setIp(transaction.getIp());
        transactionEntity.setNumber(transaction.getNumber());
        transactionEntity.setRegion(Region.valueOf(transaction.getRegion()));
        transactionEntity.setDate(transaction.getDate());
        transactionRepository.save(transactionEntity);


        return Optional.of(transfer(transaction));
    }


    private TransactionResponse transfer(Transaction transaction) {
        var amount = transaction.getAmount();
        boolean suspiciousIp = ipRepository.existsByIp(transaction.getIp());
        boolean stolenCardNumber = stolenCreditCard.existsByNumber(transaction.getNumber());

        if (amount <= ALLOWED_AMOUNT && !suspiciousIp && !stolenCardNumber) {
            return new TransactionResponse(properties.getAllowed(), "none");
        }

        if (amount <= MANUAL_PROCESSING_AMOUNT && !suspiciousIp && !stolenCardNumber) {
            return new TransactionResponse(properties.getManualProcessing(), "amount");
        }

        return getTransactionResponse(amount, suspiciousIp, stolenCardNumber);
    }

    private TransactionResponse getTransactionResponse(long amount, boolean suspiciousIp, boolean stolenCardNumber) {
        if (amount > MANUAL_PROCESSING_AMOUNT && suspiciousIp && stolenCardNumber) {
            return new TransactionResponse(properties.getProhibited(), "amount, card-number, ip");
        } else if (suspiciousIp && amount > MANUAL_PROCESSING_AMOUNT) {
            return new TransactionResponse(properties.getProhibited(), "amount, ip");
        } else if (stolenCardNumber && amount > MANUAL_PROCESSING_AMOUNT) {
            return new TransactionResponse(properties.getProhibited(), "amount, card-number");
        } else if (suspiciousIp && stolenCardNumber) {
            return new TransactionResponse(properties.getProhibited(), "card-number, ip");
        } else if (suspiciousIp) {
            return new TransactionResponse(properties.getProhibited(), "ip");
        } else if (stolenCardNumber) {
            return new TransactionResponse(properties.getProhibited(), "card-number");
        } else {
            return new TransactionResponse(properties.getProhibited(), "amount");
        }
    }

}
