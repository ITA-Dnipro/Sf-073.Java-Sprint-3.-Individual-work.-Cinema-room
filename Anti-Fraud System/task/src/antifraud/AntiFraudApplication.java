package antifraud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication
public class AntiFraudApplication {
    public static void main(String[] args) {
        SpringApplication.run(AntiFraudApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(Environment env) {
        return args -> {
            log.info("ALLOWED value is {}", env.getRequiredProperty("ALLOWED"));
            log.info("MANUAL_PROCESSING value is {}", env.getRequiredProperty("MANUAL_PROCESSING"));
        };
    }

}