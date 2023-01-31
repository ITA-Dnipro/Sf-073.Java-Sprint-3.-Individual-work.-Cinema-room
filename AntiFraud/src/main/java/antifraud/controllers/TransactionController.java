package antifraud.controllers;

import antifraud.domain.models.request.TransactionRequest;
import antifraud.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping(path = "/antifraud/transaction",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> checkTransactionState(@RequestBody TransactionRequest transactionRequest) {
        Optional<Long> optionalTransactionRequest = Optional.ofNullable(transactionRequest.getAmount());
        if (optionalTransactionRequest.isEmpty() || optionalTransactionRequest.get() <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return transactionService.getTransactionState(optionalTransactionRequest.get());
    }
}
