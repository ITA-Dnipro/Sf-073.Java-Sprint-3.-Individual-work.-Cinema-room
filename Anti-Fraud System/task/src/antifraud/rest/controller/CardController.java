package antifraud.rest.controller;

import antifraud.domain.model.Card;
import antifraud.domain.service.CardService;
import antifraud.exceptions.ExistingCardException;
import antifraud.rest.dto.CardDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/antifraud/stolencard")
public class CardController {
    private final CardService cardService;

    @PostMapping()
    public CardDTO saveStolenCard(@Valid @RequestBody CardDTO cardDTO) {
        Card storedCard = cardService.storeStolenCard(cardDTO.toModel())
                .orElseThrow(() -> new ExistingCardException(HttpStatus.CONFLICT));
        return CardDTO.fromModel(storedCard);
    }
}