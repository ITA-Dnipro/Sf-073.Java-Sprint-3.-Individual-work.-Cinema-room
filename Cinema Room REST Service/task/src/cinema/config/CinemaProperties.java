package cinema.config;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Positive;

@Slf4j
@lombok.Value
@Validated
@ConstructorBinding
@ConfigurationProperties("cinema")
//@Component - if we remove @ConstructorBinding we need @Component
//@Getter@Setter - if we remove @Value(from Lombok), we need get/set
public class CinemaProperties {
    //    @Value("${cinema.total-rows:9}") - for this to work we need to remove @ConfigurationProperties
    @Positive
    int totalRows;
    //    @Value("${cinema.total-columns:9}")
    @Positive
    int totalColumns;

    //    @Value("${cinema.front-rows:4}")
    @Positive
    int frontRows;

    Prices prices;


    @PostConstruct
    public void logLoaded() {
        log.info("-> totalRows - {}", totalRows);
        log.info("-> totalColumns - {}", totalColumns);
        log.info("-> frontRows - {}", frontRows);
        log.info("-> frontRowsPrice - {}", prices.getFrontRows());
        log.info("-> backRowsPrice - {}", prices.getBackRows());
    }

    @Value
    @Validated
    @ConstructorBinding
    public static class Prices {
        @Positive
        int frontRows;
        @Positive
        int backRows;
    }
}

