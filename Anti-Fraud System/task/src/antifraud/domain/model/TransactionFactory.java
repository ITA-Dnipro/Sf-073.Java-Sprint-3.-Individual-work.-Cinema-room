package antifraud.domain.model;

public class TransactionFactory {

    private TransactionFactory() {
    }

    public static Transaction create(Long depositMoney) {
        return Transaction.builder()
                .money(depositMoney)
                .build();
    }
}