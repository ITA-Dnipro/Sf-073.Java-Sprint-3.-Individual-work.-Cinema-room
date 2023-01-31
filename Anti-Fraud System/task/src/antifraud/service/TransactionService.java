package antifraud.service;

import antifraud.model.Transaction;
import antifraud.model.TransactionResult;
import antifraud.model.TransactionResultResponse;
import antifraud.repository.CardRepository;
import antifraud.repository.IpRepository;
import antifraud.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    CardRepository cardRepository;
    @Autowired
    IpRepository ipRepository;
    @Autowired
    TransactionRepository transactionRepository;
    public TransactionResultResponse transactionResult(Transaction transaction){
        List<String> transactionInfo = new ArrayList<>();
        boolean prohibited = false;
        TransactionResult result;
        if(cardRepository.findByNumber(transaction.getNumber()).isPresent()){
            prohibited=true;
            transactionInfo.add("card-number");
        }
         if(ipRepository.findByIp(transaction.getIp()).isPresent()){
            prohibited=true;
            transactionInfo.add("ip");
        }
        if(transaction.getAmount()<=200){
            result=TransactionResult.ALLOWED;
            transactionInfo.add("none");
        }
        else if(transaction.getAmount()<=1500){
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
            Collections.sort(transactionInfo);
        }
        transactionRepository.save(transaction);
        List<Transaction> transactionHistory = transactionRepository.findByNumberAndDateBetween
                (transaction.getNumber(), transaction.getDate().minusHours(1), transaction.getDate());
        long distinctIps = transactionHistory.stream().map(Transaction::getIp).distinct().count();
        long distinctRegions = transactionHistory.stream().map(Transaction::getRegion).distinct().count();

        if (checkNumber(distinctRegions).equals(TransactionResult.PROHIBITED)) {
            transactionInfo.removeIf(e-> e.contains("none"));
            result=TransactionResult.PROHIBITED;
            transactionInfo.add("region-correlation");
        } else if (checkNumber(distinctRegions).equals(TransactionResult.MANUAL_PROCESSING)) {
            result=TransactionResult.MANUAL_PROCESSING;
            transactionInfo.removeIf(e-> e.contains("none"));
            transactionInfo.add("region-correlation");
        }

        if (checkNumber(distinctIps).equals(TransactionResult.PROHIBITED)) {
            transactionInfo.removeIf(e-> e.contains("none"));
            result=TransactionResult.PROHIBITED;
            transactionInfo.add("ip-correlation");
        } else if (checkNumber(distinctIps).equals(TransactionResult.MANUAL_PROCESSING)) {
            result = TransactionResult.MANUAL_PROCESSING;
            transactionInfo.removeIf(e-> e.contains("none"));
            transactionInfo.add("ip-correlation");
        }
        return new TransactionResultResponse(result, String.join(", ", transactionInfo));
    }
    private TransactionResult checkNumber(long number){
        if (number <= 2) {
            return TransactionResult.ALLOWED;
        }
        else if(number==3){
            return TransactionResult.MANUAL_PROCESSING;
        }
        else{
            return TransactionResult.PROHIBITED;
        }

    }
}
