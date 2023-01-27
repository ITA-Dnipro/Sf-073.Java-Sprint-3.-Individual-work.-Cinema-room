package antifraud.controller;

import antifraud.model.CardDTO;
import antifraud.model.CardDeleteResponse;
import antifraud.model.CardResponse;
import antifraud.model.IpResponse;
import antifraud.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CardController {
    @Autowired
    CardService cardService;
    @PostMapping("/api/antifraud/stolencard")
    CardResponse saveCard(@RequestBody CardDTO card){
       return cardService.saveCard(card);
    }
    @DeleteMapping("/api/antifraud/stolencard/{number}")
    CardDeleteResponse deleteCard(@RequestBody @PathVariable String number){
        return cardService.deleteByNumber(number);
    }
    @GetMapping("/api/antifraud/stolencard")
    List<CardResponse> findAll(){
        return cardService.findAll();
    }

}
