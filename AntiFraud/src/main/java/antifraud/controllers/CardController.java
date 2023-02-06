package antifraud.controllers;

import antifraud.domain.models.dto.CardDto;
import antifraud.domain.models.request.CardStolenCreateRequest;
import antifraud.domain.models.request.CardStolenDeleteRequest;
import antifraud.domain.models.response.CardStolenCreateResponse;
import antifraud.domain.models.response.CardStolenDeleteResponse;
import antifraud.services.contracts.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@PreAuthorize("hasRole('SUPPORT')")
@RestController
@RequestMapping("/api/antifraud")
public class CardController {

    @Autowired
    CardService cardService;

    @PostMapping(value = "/stolencard")
    ResponseEntity<CardStolenCreateResponse> saveStolenCard(@Valid @RequestBody CardStolenCreateRequest cardStolenCreateRequest) {
        CardDto newCardDto = cardService.saveStolenCard(cardStolenCreateRequest);
        return new ResponseEntity<>(new CardStolenCreateResponse(newCardDto), HttpStatus.OK);
    }

    @GetMapping(value = "/stolencard")
    ResponseEntity<List<Object>> getListOfStolenCards() {
        return new ResponseEntity<>(cardService.getListOfStolenCards(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/stolencard/{number}")
    ResponseEntity<CardStolenDeleteResponse> deleteStolenCardByNumber(@Valid @PathVariable("number") CardStolenDeleteRequest cardStolenDeleteRequest) {
        cardService.deleteStolenCardByNumber(cardStolenDeleteRequest);
        return new ResponseEntity<>(new CardStolenDeleteResponse(cardStolenDeleteRequest.getNumber()), HttpStatus.OK);
    }
}
