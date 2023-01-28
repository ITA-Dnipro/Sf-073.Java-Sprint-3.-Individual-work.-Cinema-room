package antifraud.domain.model;

public class TransactionFactory {

    private TransactionFactory() {
    }

    public static Transaction create(Long depositMoney, String ipAddress, String cardNumber) {
        return Transaction.builder()
                .money(depositMoney)
                .ipAddress(ipAddress)
                .cardNumber(cardNumber)
                .build();
    }
}