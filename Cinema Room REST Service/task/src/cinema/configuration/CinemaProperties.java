package cinema.configuration;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

@ConstructorBinding
@Value
@Slf4j
@ConfigurationProperties("cinema")
@PropertySource("classpath:cinema.properties")
public class CinemaProperties {
    int totalRows;
    int totalColumns;
    int frontRows;
    int frontRowsPrice;
    int backRowsPrice;
    String adminPassword;

    @PostConstruct
    void init(){
      log.info("cinema.total-rows: {}",totalRows);
      log.info("cinema.total-columns: {}",totalColumns);
      log.info("cinema.front-rows = {}", frontRows);
      log.info("cinema.front-rows-price = {}", frontRowsPrice);
      log.info("cinema.back-rows-price = {}", backRowsPrice);
    }

}
