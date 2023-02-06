package antifraud.domain.models.response;

import antifraud.domain.models.dto.CardDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class CardStolenCreateResponse implements Serializable {

    private static final long serialVersionUID = 1040661501530161564L;
    private Long id;
    private String number;
    public CardStolenCreateResponse(CardDto cardDto) {
        this.id = cardDto.getId();
        this.number = cardDto.getNumber();
    }
}
