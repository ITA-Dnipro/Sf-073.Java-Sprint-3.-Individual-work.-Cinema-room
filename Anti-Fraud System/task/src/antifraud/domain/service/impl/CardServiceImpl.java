package antifraud.domain.service.impl;

import antifraud.domain.model.Card;
import antifraud.domain.service.CardService;
import antifraud.exceptions.CardNotFoundException;
import antifraud.persistence.repository.CardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;

    @Override
    public Optional<Card> storeStolenCardNumber(Card stolenCard) {
        return cardRepository.existsByNumber(stolenCard.getNumber()) ?
                Optional.empty() :
                Optional.of(cardRepository.save(stolenCard));
    }

    @Override
    public void removeCardNumber(String number) {
        Card foundCard = cardRepository.findByNumber(number)
                .orElseThrow(() -> new CardNotFoundException(number));
        cardRepository.deleteById(foundCard.getId());
    }

    @Override
    public List<Card> showCardNumbers() {
        return cardRepository.findAll();
    }
}