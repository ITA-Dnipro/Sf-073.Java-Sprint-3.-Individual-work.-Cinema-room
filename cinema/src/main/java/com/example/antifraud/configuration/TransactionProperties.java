package com.example.antifraud.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

@Getter
@Setter
@ConfigurationProperties("transaction")
@Slf4j
public class TransactionProperties {
    private String allowed;
    private String manualProcessing;
    private String prohibited;
    private int allowedAmount;
    private int manualProcessingAmount;

    @PostConstruct
    void logFunc() {
        log.info("allowed = {}", allowed);
        log.info("manualProcessing = {}", manualProcessing);
        log.info("prohibited = {}", prohibited);
        log.info("allowedAmount = {}", allowedAmount);
        log.info("manualProcessingAmount = {}", manualProcessingAmount);
    }
}
