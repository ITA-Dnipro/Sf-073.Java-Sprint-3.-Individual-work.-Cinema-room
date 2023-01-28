package antifraud.domain.model;

import antifraud.domain.model.enums.WorldRegion;

import java.time.LocalDateTime;

public class TransactionFactory {

    private TransactionFactory() {
    }

    public static Transaction create(Long depositMoney,
                                     String ipAddress,
                                     String cardNumber,
                                     WorldRegion region,
                                     LocalDateTime date) {
        return Transaction.builder()
                .money(depositMoney)
                .ipAddress(ipAddress)
                .cardNumber(cardNumber)
                .worldRegion(region)
                .localDateTime(date)
                .build();
    }
}