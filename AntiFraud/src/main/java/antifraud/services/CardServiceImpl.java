package antifraud.services;

import antifraud.domain.models.dao.CardEntity;
import antifraud.domain.models.dto.CardDto;
import antifraud.domain.models.request.CardStolenCreateRequest;
import antifraud.domain.models.request.CardStolenDeleteRequest;
import antifraud.domain.models.response.CardStolenCreateResponse;
import antifraud.domain.models.response.CardStolenDeleteResponse;
import antifraud.repositories.CardRepository;
import antifraud.services.contracts.CardService;
import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CardServiceImpl implements CardService {

    @Autowired
    CardRepository cardRepository;
    public CardDto saveStolenCard(CardStolenCreateRequest cardStolenCreateRequest) {
        if(!LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(cardStolenCreateRequest.getNumber())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if(isStolenCardExists(cardStolenCreateRequest.getNumber())){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        CardEntity cardEntity = new CardEntity();
        cardEntity.setNumber(cardStolenCreateRequest.getNumber());
        cardEntity.setStolen(true);
        CardEntity cardEntityUpdatedRecord = cardRepository.save(cardEntity);
        return mapCardEntityToCardDto(cardEntityUpdatedRecord);
    }
    @Override
    public List<Object> getListOfStolenCards() {
        List<CardEntity> cardEntityList = cardRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return cardEntityList
                .stream().map(entity -> new CardStolenCreateResponse(new CardDto(entity.getId(),
                        entity.getNumber())))
                        .collect(Collectors.toList());
    };

    @Override
    public CardStolenDeleteResponse deleteStolenCardByNumber(CardStolenDeleteRequest cardStolenDeleteRequest) {
        if(!LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(cardStolenDeleteRequest.getNumber())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Optional<CardEntity> cardEntity = cardRepository.findByNumber(cardStolenDeleteRequest.getNumber());
        if(cardEntity.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        cardRepository.delete(cardEntity.get());
        return new CardStolenDeleteResponse(cardEntity.get().getNumber());
    }

    @Override
    public boolean isStolenCardExists(String cardNumber){
        return cardRepository.findByNumber(cardNumber).isPresent();
    }

    private CardDto mapCardEntityToCardDto(CardEntity cardEntity){
        CardDto newCardDto = new CardDto();
        newCardDto.setId(cardEntity.getId());
        newCardDto.setNumber(cardEntity.getNumber());
        return newCardDto;
    }
}
