package antifraud.domain.service;

import antifraud.domain.model.Card;

import java.util.Optional;

public interface CardService {
    Optional<Card> storeStolenCard(Card stolenCard);
}