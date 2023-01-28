package antifraud.rest.dto;

import antifraud.domain.model.Transaction;
import antifraud.domain.model.TransactionFactory;
import antifraud.domain.model.enums.TransactionResult;
import antifraud.domain.model.enums.WorldRegion;
import antifraud.validation.IpAddress;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@JsonPropertyOrder({"amountMoney", "ipAddress", "cardNumber", "region", "date"})
@Builder
public record TransactionDTO(@Min(1)
                             @NotNull
                             @JsonProperty(value = "amount", access = JsonProperty.Access.WRITE_ONLY)
                             Long amountMoney,
                             @NotNull
                             @IpAddress
                             @JsonProperty(value = "ip", access = JsonProperty.Access.WRITE_ONLY)
                             String ipAddress,
                             @NotEmpty
                             @CreditCardNumber
                             @JsonProperty(value = "number", access = JsonProperty.Access.WRITE_ONLY)
                             String cardNumber,
                             @NotEmpty
                             @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
                             String region,
                             @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
                             LocalDateTime date,
                             @JsonProperty(value = "result", access = JsonProperty.Access.READ_ONLY)
                             TransactionResult processedTransaction,
                             @JsonProperty(value = "info", access = JsonProperty.Access.READ_ONLY)
                             String transactionInfo) {
    public static TransactionDTO fromModel(Transaction deposit) {
        return TransactionDTO.builder()
                .processedTransaction(deposit.getTransactionResult())
                .transactionInfo(deposit.getTransactionInfo())
                .build();
    }

    public Transaction toModel() {

        return TransactionFactory.create(amountMoney,
                ipAddress,
                cardNumber,
                WorldRegion.valueOf(region),
                date);
    }
}