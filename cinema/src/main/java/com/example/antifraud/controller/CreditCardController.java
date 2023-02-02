package com.example.antifraud.controller;


import com.example.antifraud.controller.dto.creditcard.CreditCardResponse;
import com.example.antifraud.controller.dto.creditcard.DeleteCreditCardResponse;
import com.example.antifraud.model.entities.CreditCardEntity;
import com.example.antifraud.service.creditcard.CreditCardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/api/antifraud")
@Validated
@AllArgsConstructor
public class CreditCardController {

    CreditCardService creditCardService;


    @PostMapping("/stolencard")
    public CreditCardResponse createIp(@Valid @RequestBody CreditCardEntity creditCardEntity) {
        return creditCardService.postStolenCard(creditCardEntity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT));
    }

    @GetMapping("/stolencard")
    public List<CreditCardResponse> getAllIps() {
        return creditCardService.getAllStolenCards();
    }

    @DeleteMapping("/stolencard/{number}")
    @ResponseStatus(HttpStatus.OK)
    public DeleteCreditCardResponse deleteUser(@PathVariable("number") @Pattern(regexp="[\\d]{16}") String number) {
        if (creditCardService.delete(number)) {
            String msg = "Card " + number + " successfully removed!";
            return new DeleteCreditCardResponse(msg);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>(
                "not valid due to validation error: " +
                        e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
