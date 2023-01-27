package antifraud.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
@Getter@Setter@AllArgsConstructor
public class Transaction {
    @Positive
    @NotNull
    Long amount;
    @NotEmpty
    String ip;
    @NotEmpty
    String number;

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
