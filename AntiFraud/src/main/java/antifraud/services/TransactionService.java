package antifraud.services;

import antifraud.domain.models.TransactionState;
import antifraud.domain.models.response.TransactionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    public TransactionState setTransactionState(Long amount){
        if(amount <= 200){
            return TransactionState.ALLOWED;
        }else  if(amount <= 1500){
            return TransactionState.MANUAL_PROCESSING;
        }
        return TransactionState.PROHIBITED;
    }

    public ResponseEntity<Object> getTransactionState(long amount) {
        TransactionResponse transactionResponse = new TransactionResponse(String.valueOf(setTransactionState(amount)));
        return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
    }
}
