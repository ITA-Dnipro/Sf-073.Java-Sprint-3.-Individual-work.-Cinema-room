package antifraud.service;

import antifraud.model.*;
import antifraud.repository.CardRepository;
import antifraud.repository.CleanCardRepository;
import antifraud.repository.IpRepository;
import antifraud.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Slf4j
@Service
public class TransactionService {
    @Autowired
    CardService cardService;
    @Autowired
    IpRepository ipRepository;
    @Autowired
    TransactionRepository transactionRepository;
    final
    CleanCardRepository cleanCardRepository;
    public TransactionService(CleanCardRepository cleanCardRepository) {
        this.cleanCardRepository = cleanCardRepository;
    }

    public List<Transaction> transactions(){
        return transactionRepository.findAll();
    }
    int maxAllowed = 200;
    int maxManual = 1500;
    public TransactionResultResponse transactionResult(Transaction transaction){

        List<String> transactionInfo = new ArrayList<>();
        boolean prohibited = false;
        TransactionResult result;
        if(cardService.findCardByNumber(transaction.getNumber()).isPresent()){
            prohibited=true;
            transactionInfo.add("card-number");
        }
        else{
            Optional<CleanCard> card = cleanCardRepository.findByNumber(transaction.getNumber());
            if(card.isPresent()){
                maxAllowed = card.get().getMaxAllowed();
                 maxManual = card.get().getMaxManual();
            }
            else {
                CleanCard newCard = new CleanCard();
                newCard.setNumber(transaction.getNumber());
                newCard.setMaxAllowed(maxAllowed);
                newCard.setMaxManual(maxManual);
                if (cleanCardRepository.findByNumber(transaction.getNumber()).isEmpty()) {
                    cleanCardRepository.save(newCard);
                }
            }
        }
         if(ipRepository.findByIp(transaction.getIp()).isPresent()){
            prohibited=true;
            transactionInfo.add("ip");
        }

        if(transaction.getAmount()<= maxAllowed){
            result=TransactionResult.ALLOWED;
            transactionInfo.add("none");
        }
        else {

            if(transaction.getAmount()<= maxManual){
                result=TransactionResult.MANUAL_PROCESSING;
                if(!prohibited) {
                    transactionInfo.add("amount");
                }
            }
            else{
                result = TransactionResult.PROHIBITED;
                transactionInfo.add("amount");
            }
        }
        transactionRepository.save(transaction);
        List<Transaction> transactionHistory = transactionRepository.findByNumberAndDateBetween
                (transaction.getNumber(), transaction.getDate().minusHours(1), transaction.getDate());
        long distinctIps = transactionHistory.stream().map(Transaction::getIp).distinct().count();
        log.info("----------------------- distinctIps {}", distinctIps);
        long distinctRegions = transactionHistory.stream().map(Transaction::getRegion).distinct().count();
        log.info("----------------------- distinctRegions {}", distinctRegions);

        if (checkNumber(distinctRegions).equals(TransactionResult.PROHIBITED)) {
            result=TransactionResult.PROHIBITED;
            transactionInfo.removeIf(e-> e.contains("none"));
            transactionInfo.add("region-correlation");
        } else if (checkNumber(distinctRegions).equals(TransactionResult.MANUAL_PROCESSING)) {
            result=TransactionResult.MANUAL_PROCESSING;
            transactionInfo.removeIf(e-> e.contains("none"));
            transactionInfo.add("region-correlation");
        }

        if (checkNumber(distinctIps).equals(TransactionResult.PROHIBITED)) {
            result=TransactionResult.PROHIBITED;
            transactionInfo.removeIf(e-> e.contains("none"));
            log.info("------------------- {}",result.toString());
            transactionInfo.add("ip-correlation");
        } else if (checkNumber(distinctIps).equals(TransactionResult.MANUAL_PROCESSING)) {
            result = TransactionResult.MANUAL_PROCESSING;
            log.info("------------------- {}",result.toString());
            transactionInfo.removeIf(e-> e.contains("none"));
            transactionInfo.add("ip-correlation");
        }
        if(prohibited){
            result=TransactionResult.PROHIBITED;
            log.info("------------------- {}",result.toString());
            Collections.sort(transactionInfo);
        }
        log.info("------------------- {}",result.toString());
        transaction.setResult(result.toString());
        transactionRepository.save(transaction);
        return new TransactionResultResponse(result, String.join(", ", transactionInfo));
    }
    private TransactionResult checkNumber(long number){
        if (number <= 2) {
            return TransactionResult.ALLOWED;
        }
        return number == 3 ? TransactionResult.MANUAL_PROCESSING :
                TransactionResult.PROHIBITED;
    }

    public Transaction updateFeedback(TransactionFeedback tsfb) {
        if(!getEnums().contains(tsfb.getFeedback())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Transaction foundTransaction = transactionRepository.findById(tsfb.getTransactionId())
                .orElseThrow(
                ()->  new ResponseStatusException(HttpStatus.NOT_FOUND));
        log.info("_________________________ transaction id {}",tsfb.getTransactionId().toString());
        log.info("_________________________ transaction number {}",foundTransaction.getNumber());
        CleanCard card = cleanCardRepository.findByNumber(foundTransaction.getNumber()).get();
        if (tsfb.getFeedback().equals(foundTransaction.getResult())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        log.info("------------------- transaction feedback {}",foundTransaction.getFeedback() + " " + "HUI");
        if(!foundTransaction.getFeedback().isEmpty()){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
         if(tsfb.getFeedback().equals(TransactionResult.ALLOWED.toString())){
             maxAllowed = (int) Math.ceil(0.8*maxAllowed+0.2*foundTransaction.getAmount());
             log.info("------------------- max allowed {}", maxAllowed);
             if(foundTransaction.getResult().equals(TransactionResult.PROHIBITED.toString())){
                maxAllowed = (int) Math.ceil(0.8*maxAllowed+0.2*foundTransaction.getAmount());
                maxManual=(int) Math.ceil(0.8*maxManual+0.2*foundTransaction.getAmount());
            }
        }
        log.info("------------------- transaction feedback {}",foundTransaction.getResult() + " " + "HUI");
        log.info("------------------- transaction feedback {}",tsfb.getFeedback());
         if(tsfb.getFeedback().equals(TransactionResult.
                MANUAL_PROCESSING.toString())){
             if(foundTransaction.getResult().equals(TransactionResult.ALLOWED.toString())) {
                 maxAllowed = (int) Math.ceil(0.8*maxAllowed-0.2*foundTransaction.getAmount());
             }
             else if(foundTransaction.getResult().equals(TransactionResult.PROHIBITED.toString())){
                 maxManual=(int) Math.ceil(0.8*maxManual+0.2*foundTransaction.getAmount());
             }
        }
         if(tsfb.getFeedback().equals(TransactionResult.PROHIBITED.toString())){
             if(foundTransaction.getResult().equals(TransactionResult.ALLOWED.toString())){
                 maxAllowed = (int) Math.ceil(0.8*maxAllowed-0.2-foundTransaction.getAmount());
                 maxManual=(int) Math.ceil(0.8*maxManual-0.2*foundTransaction.getAmount());
                 log.info("------------------- max allowed {}", maxAllowed);
             }
             else if(foundTransaction.getResult().equals(TransactionResult.MANUAL_PROCESSING.toString())){
                 maxManual=(int) Math.ceil(0.8*maxManual-0.2*foundTransaction.getAmount());
             }

         }
        log.info("------------------- max allowed {}", maxAllowed);
        log.info("------------------- max manual {}", maxManual);
         card.setMaxAllowed(maxAllowed);
         card.setMaxManual(maxManual);
         cleanCardRepository.save(card);
       return transactionRepository.save(new Transaction(tsfb.getTransactionId(), foundTransaction.getAmount(),
                foundTransaction.getIp(), foundTransaction.getNumber(), foundTransaction.getRegion(),
                foundTransaction.getDate(),
                foundTransaction.getResult(), tsfb.getFeedback()));
    }

    public List<Transaction> historyForCard(String number) {
        if(!LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(number)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if(transactionRepository.findAllByNumber(number).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
            return transactionRepository.findAllByNumber(number);

    }
    public static HashSet<String> getEnums() {

        HashSet<String> values = new HashSet<String>();

        for (TransactionResult c : TransactionResult.values()) {
            values.add(c.name());
        }

        return values;
    }
}
