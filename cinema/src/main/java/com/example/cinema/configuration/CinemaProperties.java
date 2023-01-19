package com.example.cinema.configuration;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;


import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Getter@Setter
@ConfigurationProperties("cinema")
@Validated
public class CinemaProperties {
    @Positive
    private int totalRows;
    @Positive
    private int totalColumns;
    @Positive
    int frontRows;
    Prices prices;

    @Value
    @Validated
    public static class Prices {
        @Positive
        int frontRows;
        @Positive
        int backRows;

        @ConstructorBinding
        public Prices(int frontRows, int backRows) {
            this.frontRows = frontRows;
            this.backRows = backRows;
        }
    }

    @PostConstruct
    void logLoaded() {
        log.info("== totalRows = {}", totalRows);
        log.info("== totalColumns = {}", totalColumns);
        log.info("== frontRows = {}", frontRows);
        log.info("== frontRowsPrice = {}", prices.getFrontRows());
        log.info("== backRowsPrice = {}", prices.getBackRows());
    }
}
