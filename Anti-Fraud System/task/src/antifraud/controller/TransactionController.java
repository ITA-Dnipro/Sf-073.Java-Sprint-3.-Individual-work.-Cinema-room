package antifraud.controller;

import antifraud.model.Transaction;
import antifraud.model.TransactionFeedback;
import antifraud.model.TransactionResult;
import antifraud.model.TransactionResultResponse;
import antifraud.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TransactionController {
    @Autowired
    TransactionService ts;

    @PostMapping("/api/antifraud/transaction")
    TransactionResultResponse foo(@RequestBody @Valid Transaction req) {
        return ts.transactionResult(req);
    }
    @PutMapping( "/api/antifraud/transaction")
    Transaction updateFeedback(@RequestBody TransactionFeedback tsfb){
        return ts.updateFeedback(tsfb);
    }

    @GetMapping("/api/antifraud/history")
    List<Transaction> getHistory() {
        return ts.transactions();
    }
    @GetMapping("/api/antifraud/history/{number}")
    List<Transaction> getCard(@PathVariable String number) {
        return ts.historyForCard(number);
    }

}
