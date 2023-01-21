package antifraud.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class Transaction {
    @Positive
    @NotNull
    Long amount;

    public Transaction(Long amount) {
        this.amount = amount;
    }

    public Transaction() {

    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
