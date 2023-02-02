package com.example.antifraud.controller.dto.creditcard;


import com.example.antifraud.model.entities.CreditCardEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CreditCardResponse {
    Long id;
    String number;

    public CreditCardResponse(CreditCardEntity creditCardEntity) {
        id = creditCardEntity.getId();
        number = creditCardEntity.getNumber();
    }
}
