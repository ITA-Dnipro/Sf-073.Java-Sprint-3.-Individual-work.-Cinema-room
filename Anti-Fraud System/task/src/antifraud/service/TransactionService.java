package antifraud.service;

import antifraud.model.TransactionResult;
import antifraud.model.TransactionResultResponse;
import antifraud.repository.CardRepository;
import antifraud.repository.IpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    CardRepository cardRepository;
    @Autowired
    IpRepository ipRepository;
    public TransactionResultResponse transactionResult(long amount, String ip, String number){
        List<String> transactionInfo = new ArrayList<>();
        boolean prohibited = false;
        TransactionResult result;
        if(cardRepository.findByNumber(number).isPresent()){
            prohibited=true;
            transactionInfo.add("card-number");
        }
         if(ipRepository.findByIp(ip).isPresent()){
            prohibited=true;
            transactionInfo.add("ip");
        }
        if(amount<=200){
            result=TransactionResult.ALLOWED;
            transactionInfo.add("none");
        }
        else if(amount<=1500){
            result=TransactionResult.MANUAL_PROCESSING;
            if(!prohibited) {
                transactionInfo.add("amount");
            }
        }
        else{
            result = TransactionResult.PROHIBITED;
            transactionInfo.add("amount");
        }
        if(prohibited){
            result=TransactionResult.PROHIBITED;
        }
        Collections.sort(transactionInfo);
        return new TransactionResultResponse(result, String.join(", ", transactionInfo));
    }
}
