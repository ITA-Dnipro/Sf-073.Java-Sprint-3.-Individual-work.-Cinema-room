package antifraud.rest.dto;

import antifraud.domain.model.Card;
import antifraud.domain.model.CardFactory;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.NotEmpty;

@Builder
public record CardDTO(@JsonProperty(access = JsonProperty.Access.READ_ONLY)
                      Long id,
                      @NotEmpty
                      @CreditCardNumber
                      String number) {
    public static CardDTO fromModel(Card storedCard) {
        return CardDTO.builder()
                .id(storedCard.getId())
                .number(storedCard.getNumber())
                .build();
    }

    public Card toModel() {
        return CardFactory.create(number);
    }
}