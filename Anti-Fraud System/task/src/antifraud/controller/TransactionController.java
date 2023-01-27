package antifraud.controller;

import antifraud.model.Transaction;
import antifraud.model.TransactionResult;
import antifraud.model.TransactionResultResponse;
import antifraud.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TransactionController {
    @Autowired
    TransactionService ts;
        @PostMapping("/api/antifraud/transaction")
        TransactionResultResponse foo(@RequestBody @Valid Transaction req){
           return ts.transactionResult(req.getAmount(), req.getIp(), req.getNumber());
        }

}
