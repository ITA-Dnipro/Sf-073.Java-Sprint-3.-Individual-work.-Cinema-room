package antifraud.domain.models.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Getter
public class CardStolenDeleteResponse implements Serializable {

    String status;

    @JsonIgnore
    String cardNumber;

    public CardStolenDeleteResponse(String cardNumber) {
        this.cardNumber = cardNumber;
        setStatus(cardNumber);
    }

    private void setStatus(String cardNumber) {
        this.status = "Card " + cardNumber + " successfully removed!";
    }
}
