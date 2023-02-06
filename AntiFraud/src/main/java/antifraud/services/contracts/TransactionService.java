package antifraud.services.contracts;

import antifraud.domain.models.request.TransactionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {


    ResponseEntity<Object> getTransactionState(TransactionRequest transactionRequest);
}
