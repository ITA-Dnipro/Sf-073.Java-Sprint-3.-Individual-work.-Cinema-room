package antifraud.services;

import antifraud.domain.models.TransactionState;
import antifraud.domain.models.request.TransactionRequest;
import antifraud.domain.models.response.TransactionResponse;
import antifraud.services.contracts.CardService;
import antifraud.services.contracts.IpService;
import antifraud.services.contracts.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    CardService cardService;
    @Autowired
    IpService ipService;
    @Override
    public ResponseEntity<Object> getTransactionState(TransactionRequest transactionRequest) {
        TransactionResponse transactionResponse = new TransactionResponse();
        List<String> info = new ArrayList<>();
        createResponse(transactionRequest, transactionResponse, info);
        String join = String.join(", ", info);
        transactionResponse.setInfo(join);
        return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
    }

    private void createResponse(TransactionRequest transactionRequest, TransactionResponse transactionResponse, List<String> info) {
        long amount = transactionRequest.getAmount();
        if (amount <= 200L) {
            transactionResponse.setResult(TransactionState.ALLOWED.name());
            info.add("none");
        } else if (amount <= 1500L) {
            transactionResponse.setResult(TransactionState.MANUAL_PROCESSING.name());
            info.add("amount");
        } else {
            transactionResponse.setResult(TransactionState.PROHIBITED.name());
            info.add("amount");
        }

        if (cardService.isStolenCardExists(transactionRequest.getNumber())) {
            transactionResponse.setResult(TransactionState.PROHIBITED.name());
            info.add("card-number");
        }
        if (ipService.isIpExists(transactionRequest.getIp())) {
            transactionResponse.setResult(TransactionState.PROHIBITED.name());
            info.add("ip");
        }

        if (info.contains("ip") || info.contains("card-number")) {
            if (amount <= 1500L) {
                info.remove("amount");
            }
        }
    }
}
