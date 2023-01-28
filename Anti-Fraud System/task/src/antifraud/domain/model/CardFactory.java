package antifraud.domain.model;

public class CardFactory {

    private CardFactory() {
    }

    public static Card create(String number) {
        return Card.builder()
                .number(number)
                .build();
    }
}