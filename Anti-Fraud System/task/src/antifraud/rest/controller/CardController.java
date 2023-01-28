package antifraud.rest.controller;

import antifraud.domain.model.Card;
import antifraud.domain.service.CardService;
import antifraud.exceptions.ExistingCardException;
import antifraud.rest.dto.CardDTO;
import antifraud.rest.dto.CustomMessageDTO;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/api/antifraud/stolencard")
public class CardController {
    private final CardService cardService;

    @PostMapping()
    public CardDTO saveStolenCard(@Valid @RequestBody CardDTO cardDTO) {
        Card storedCard = cardService.storeStolenCardNumber(cardDTO.toModel())
                .orElseThrow(() -> new ExistingCardException(HttpStatus.CONFLICT));
        return CardDTO.fromModel(storedCard);
    }

    @DeleteMapping("/{number}")
    public CustomMessageDTO deleteCardNumber(@CreditCardNumber @PathVariable String number) {
        cardService.removeCardNumber(number);
        String returnMessage = String.format("Card %s successfully removed!", number);
        return new CustomMessageDTO(returnMessage);
    }

    @GetMapping()
    public List<CardDTO> getCardNumbers() {
        List<Card> allCardNumbers = cardService.showCardNumbers();
        return allCardNumbers.stream()
                .map(CardDTO::fromModel)
                .toList();
    }
}