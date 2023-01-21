package antifraud.service;

import antifraud.model.TransactionResult;
import antifraud.model.TransactionResultResponse;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    public TransactionResult transactionResult(long amount){
        if(amount<=200){
            return TransactionResult.ALLOWED;
        }
        else if(amount<=1500){
            return TransactionResult.MANUAL_PROCESSING;
        }
        else{
           return TransactionResult.PROHIBITED;
        }
    }
}
