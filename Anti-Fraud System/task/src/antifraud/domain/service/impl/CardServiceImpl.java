package antifraud.domain.service.impl;

import antifraud.domain.model.Card;
import antifraud.domain.service.CardService;
import antifraud.persistence.repository.CardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;

    @Override
    public Optional<Card> storeStolenCard(Card stolenCard) {
        return cardRepository.existsByNumber(stolenCard.getNumber()) ?
                Optional.empty() :
                Optional.of(cardRepository.save(stolenCard));
    }
}