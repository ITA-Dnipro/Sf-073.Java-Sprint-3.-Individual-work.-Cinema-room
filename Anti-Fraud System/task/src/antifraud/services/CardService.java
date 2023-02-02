package antifraud.services;

import antifraud.models.database.Card;
import antifraud.models.DTO.CardResponse;

import java.util.List;
import java.util.Optional;

public interface CardService {
    CardResponse saveCard(Card stolenCard);

    void deleteCardFromDB(String number);

    List<CardResponse> findAllCards();
    Optional<Card> findByCardNumber(String number);

}
