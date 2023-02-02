package com.example.antifraud.service.creditcard;


import com.example.antifraud.controller.dto.creditcard.CreditCardResponse;
import com.example.antifraud.model.entities.CreditCardEntity;
import com.example.antifraud.repository.StolenCreditCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class CreditCardServiceImpl implements CreditCardService {

    @Autowired
    StolenCreditCard stolenCreditCard;

    @Override
    @Transactional
    public Optional<CreditCardResponse> postStolenCard(CreditCardEntity creditCardEntity) {
        if (stolenCreditCard.existsByNumber(creditCardEntity.getNumber())) {
            return Optional.empty();
        }

        creditCardEntity.setNumber(creditCardEntity.getNumber());
        stolenCreditCard.save(creditCardEntity);

        return Optional.of( new CreditCardResponse(creditCardEntity)) ;
    }

    @Override
    @Transactional
    public boolean delete(String number) {
        int result = stolenCreditCard.deleteByNumber(number);
        return result == 1;
    }

    @Override
    @Transactional
    public List<CreditCardResponse> getAllStolenCards() {
        List<CreditCardResponse> creditCards = new ArrayList<>();
        stolenCreditCard.findAll(Sort.by("id"))
                .forEach(number -> creditCards.add(
                        new CreditCardResponse(number)
                ));
        return creditCards;
    }
}
