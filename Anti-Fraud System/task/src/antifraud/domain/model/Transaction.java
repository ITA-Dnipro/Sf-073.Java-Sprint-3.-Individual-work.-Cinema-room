package antifraud.domain.model;

import antifraud.domain.model.enums.TransactionResult;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PACKAGE)
public class Transaction {
    private Long money;
    private String ipAddress;
    private String cardNumber;
    private TransactionResult transactionResult;
    private String transactionInfo;
}