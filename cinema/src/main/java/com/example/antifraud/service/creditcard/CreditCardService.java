package com.example.antifraud.service.creditcard;


import com.example.antifraud.controller.dto.creditcard.CreditCardResponse;
import com.example.antifraud.model.entities.CreditCardEntity;

import java.util.List;
import java.util.Optional;

public interface CreditCardService {
    Optional<CreditCardResponse> postStolenCard(CreditCardEntity creditCardEntity);
    boolean delete(String number);
    List<CreditCardResponse> getAllStolenCards();
}
