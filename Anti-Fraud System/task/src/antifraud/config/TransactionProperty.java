package antifraud.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Positive;

@Slf4j
@ConfigurationProperties(prefix = "transaction.values")
@Validated
public record TransactionProperty(@Positive
                                  int allowed,
                                  @Positive
                                  int manualProcessing,
                                  @Positive
                                  int correlation) {

    @Bean
    CommandLineRunner runner(TransactionProperty transactionProperty) {
        return args -> {
            log.info("ALLOWED value is {}", transactionProperty.allowed());
            log.info("MANUAL_PROCESSING value is {}", transactionProperty.manualProcessing());
            log.info("Correlation value based on unique regions is {}", transactionProperty.correlation());
        };
    }
}