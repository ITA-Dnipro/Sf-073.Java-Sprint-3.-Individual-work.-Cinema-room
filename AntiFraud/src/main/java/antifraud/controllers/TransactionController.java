package antifraud.controllers;

import antifraud.domain.models.request.TransactionRequest;
import antifraud.services.contracts.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;
    @PreAuthorize(value="hasRole('MERCHANT')")
    @PostMapping(path = "api/antifraud/transaction")
    public ResponseEntity<Object> checkTransactionState(@RequestBody TransactionRequest transactionRequest) {
        Optional<Long> optionalTransactionRequest = Optional.ofNullable(transactionRequest.getAmount());
        if (optionalTransactionRequest.isEmpty() || optionalTransactionRequest.get() <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return transactionService.getTransactionState(transactionRequest);
    }
}
