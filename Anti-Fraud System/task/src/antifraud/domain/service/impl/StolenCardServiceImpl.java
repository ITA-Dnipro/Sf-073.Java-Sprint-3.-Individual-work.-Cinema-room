package antifraud.domain.service.impl;

import antifraud.domain.model.Card;
import antifraud.domain.service.StolenCardService;
import antifraud.exceptions.CardNotFoundException;
import antifraud.persistence.repository.StolenCardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StolenCardServiceImpl implements StolenCardService {
    private final StolenCardRepository stolenCardRepository;

    @Transactional
    @Override
    public Optional<Card> storeStolenCardNumber(Card stolenCard) {
        return stolenCardRepository.existsByNumber(stolenCard.getNumber()) ?
                Optional.empty() :
                Optional.of(stolenCardRepository.save(stolenCard));
    }

    @Transactional
    @Override
    public void removeCardNumber(String number) {
        Card foundCard = stolenCardRepository.findByNumber(number)
                .orElseThrow(() -> new CardNotFoundException(number));
        stolenCardRepository.deleteById(foundCard.getId());
    }

    @Override
    public List<Card> showCardNumbers() {
        return stolenCardRepository.findAll();
    }
}