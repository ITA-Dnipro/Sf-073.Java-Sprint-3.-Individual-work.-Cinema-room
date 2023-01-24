package antifraud.rest.dto;

import antifraud.domain.model.Transaction;
import antifraud.domain.model.constants.TransactionResult;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Builder
public record TransactionDTO(@Min(1)
                             @NotNull
                             @JsonProperty(value = "amount", access = JsonProperty.Access.WRITE_ONLY)
                             Long amountMoney,
                             @JsonProperty(value = "result", access = JsonProperty.Access.READ_ONLY)
                             TransactionResult processedTransaction) {
    public static TransactionDTO fromModel(Transaction deposit) {
        return TransactionDTO.builder()
                .processedTransaction(deposit.getTransactionResult())
                .build();
    }

    public Transaction toModel() {
        return new Transaction(amountMoney);
    }

}