package antifraud.config.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Positive;

@Slf4j
@ConfigurationProperties(prefix = "transaction.values")
@Getter
@Setter
@AllArgsConstructor
@ConstructorBinding
@Validated
public class TransactionProperty {
    @Positive
    private int allowed;
    @Positive
    private int manualProcessing;
    @Positive
    private int correlation;
    @Positive
    private double currentLimitFactor;
    @Positive
    private double currentDepositFactor;

    @Bean
    CommandLineRunner runner(TransactionProperty transactionProperty) {
        return args -> {
            log.info("ALLOWED value limit is {}", transactionProperty.getAllowed());
            log.info("MANUAL_PROCESSING value limit is {}", transactionProperty.getManualProcessing());
            log.info("Correlation value based on unique IPs or regions is {}", transactionProperty.getCorrelation());
            log.info("Coefficient for current transaction limit is {}", transactionProperty.getCurrentLimitFactor());
            log.info("Coefficient for deposit is {}", transactionProperty.getCurrentDepositFactor());
        };
    }
}