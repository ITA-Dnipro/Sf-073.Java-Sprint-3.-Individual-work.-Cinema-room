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

@Service
public class CardService {
    @Autowired
    CardRepository cardRepository;
   public CardResponse saveCard(CardDTO cardDTO){
        if(!LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(cardDTO.getNumber())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        else if(cardRepository.findByNumber(cardDTO.getNumber()).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        cardRepository.save(cardDTO);
        return new CardResponse(cardDTO.getId(), cardDTO.getNumber());
    }
    public CardDeleteResponse deleteByNumber(String number){
        if(!LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(number)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if(cardRepository.findByNumber(number).isPresent()){
            CardDTO cardDTO = cardRepository.findByNumber(number).get();
            cardRepository.deleteById(cardDTO.getId());
            return new CardDeleteResponse("Card " + cardDTO.getNumber() + " successfully removed!");
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public List<CardResponse> findAll() {
        List<CardResponse> cards = new ArrayList<>();
        for(var card: cardRepository.findAll()){
            CardResponse cardResponse = new CardResponse();
            cardResponse.setId(card.getId());
            cardResponse.setNumber(card.getNumber());
            cards.add(cardResponse);
        }
        return cards;
    }
}
