package com.example.cinema.configuration;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Getter@Setter
@ConfigurationProperties("cinema")
@Validated
@Component
public class CinemaProperties {
    @Positive
    private int totalRows;
    private int totalColumns;
    private int frontRows;
    private int frontRowsPrice;
    private int backRowsPrice;

    @PostConstruct
    void logLoaded() {
        log.info("== totalRows = {}", totalRows);
        log.info("== totalColumns = {}", totalColumns);
        log.info("== frontRows = {}", frontRows);
        log.info("== frontRowsPrice = {}", frontRowsPrice);
        log.info("== backRowsPrice = {}", backRowsPrice);
    }
}
