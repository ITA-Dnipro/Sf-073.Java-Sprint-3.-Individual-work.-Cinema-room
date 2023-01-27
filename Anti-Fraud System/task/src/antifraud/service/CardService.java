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
    CardRepository cardRepo;
   public CardResponse saveCard(CardDTO cardDTO){
        if(!LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(cardDTO.getNumber())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        else if(cardRepo.findByNumber(cardDTO.getNumber()).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        cardRepo.save(cardDTO);
        return new CardResponse(cardDTO.getId(), cardDTO.getNumber());
    }
    public CardDeleteResponse deleteByNumber(String number){
        if(!LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(number)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if(cardRepo.findByNumber(number).isPresent()){
            CardDTO cardDTO = cardRepo.findByNumber(number).get();
            cardRepo.deleteById(cardDTO.getId());
            return new CardDeleteResponse("Card " + cardDTO.getNumber() + " successfully removed!");
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public List<CardResponse> findAll() {
        List<CardResponse> cards = new ArrayList<>();
        for(var card:cardRepo.findAll()){
            CardResponse cardResponse = new CardResponse();
            cardResponse.setId(card.getId());
            cardResponse.setNumber(card.getNumber());
            cards.add(cardResponse);
        }
        return cards;
    }
}
