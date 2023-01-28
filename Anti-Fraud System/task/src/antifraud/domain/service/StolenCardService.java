package antifraud.domain.service;

import antifraud.domain.model.Card;

import java.util.List;
import java.util.Optional;

public interface StolenCardService {
    Optional<Card> storeStolenCardNumber(Card stolenCard);

    void removeCardNumber(String number);

    List<Card> showCardNumbers();
}