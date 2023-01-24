package antifraud.domain.model;

import antifraud.domain.model.constants.TransactionResult;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Transaction {
    private Long money;
    private TransactionResult transactionResult;

    public Transaction(Long money) {
        this.money = money;
    }
}
