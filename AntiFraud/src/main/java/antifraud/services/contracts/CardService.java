package antifraud.services.contracts;

import antifraud.domain.models.dto.CardDto;
import antifraud.domain.models.request.CardStolenCreateRequest;
import antifraud.domain.models.request.CardStolenDeleteRequest;
import antifraud.domain.models.response.CardStolenDeleteResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CardService {

    CardDto saveStolenCard(CardStolenCreateRequest cardStolenCreateRequest);

    List<Object> getListOfStolenCards();

    CardStolenDeleteResponse deleteStolenCardByNumber(CardStolenDeleteRequest cardStolenDeleteRequest);

    boolean isStolenCardExists(String cardNumber);
}
