package com.example.antifraud.service;

import com.example.antifraud.configuration.TransactionProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionImpl implements TransactionService {
    private final TransactionProperties properties;

    @Override
    public String transfer(long amount) {
        if(amount <= properties.getAllowedAmount()) {
            return properties.getAllowed();
        } else if (amount <= properties.getManualProcessingAmount()) {
            return properties.getManualProcessing();
        } else {
            return properties.getProhibited();
        }
    }
}
