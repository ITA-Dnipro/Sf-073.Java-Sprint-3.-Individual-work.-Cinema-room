package cinema.config;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Positive;

@Slf4j
@Value
@ConfigurationProperties(prefix = "cinema")
@Validated
@ConstructorBinding
public class CinemaProperties {
    @Positive int totalRows;
    @Positive int totalColumns;
    @Positive int frontRows;
    @Positive int ticketPriceFront;
    @Positive int ticketPriceBack;
}