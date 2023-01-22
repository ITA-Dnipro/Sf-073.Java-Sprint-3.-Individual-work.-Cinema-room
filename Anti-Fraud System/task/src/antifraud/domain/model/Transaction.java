package antifraud.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {
    private Long money;
    private TransactionResult transactionResult;

    private Transaction() {
    }

    public Transaction(Long money) {
        this.money = money;
    }
}
