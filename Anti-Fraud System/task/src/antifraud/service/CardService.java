package antifraud.service;

import antifraud.model.*;
import antifraud.repository.CardRepository;
import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    @Autowired
    CardRepository cardRepository;

    public CardResponse saveCard(Card card){
        if(!LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(card.getNumber())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        else if(cardRepository.findByNumber(card.getNumber()).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        cardRepository.save(card);
        return new CardResponse(card.getId(), card.getNumber());
    }
    public CardDeleteResponse deleteByNumber(String number){
        if(!LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(number)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Optional<Card> cardOptional = cardRepository.findByNumber(number);
        if(cardOptional.isPresent()){
            cardRepository.deleteById(cardOptional.get().getId());
            return new CardDeleteResponse("Card " + cardOptional.get().getNumber() + " successfully removed!");
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public List<CardResponse> findAll() {
        List<CardResponse> cards = new ArrayList<>();
        for(var card: cardRepository.findAll()){
            cards.add(new CardResponse(card.getId(), card.getNumber()));
        }
        return cards;
    }
    public Optional<Card> findCardByNumber(String number){
       return cardRepository.findByNumber(number);
    }
}
