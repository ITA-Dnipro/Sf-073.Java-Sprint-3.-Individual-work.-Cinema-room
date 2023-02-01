package antifraud.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionFeedback {

    @NotNull
    Long transactionId;
    String feedback;

    public String getFeedback() {
        return Objects.requireNonNullElse(feedback, "");
    }

}
