package cinema.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Positive;

@Slf4j
@Getter
@Setter
@ConfigurationProperties(prefix = "cinema")
@PropertySource("classpath:cinema.properties")
@Validated
@Component
public class CinemaProperties {
    @Positive
    private int totalRows;
    @Positive
    private int totalColumns;
    @Positive
    private int frontRows;
    @Positive
    private int ticketPriceFront;
    @Positive
    private int ticketPriceBack;
}