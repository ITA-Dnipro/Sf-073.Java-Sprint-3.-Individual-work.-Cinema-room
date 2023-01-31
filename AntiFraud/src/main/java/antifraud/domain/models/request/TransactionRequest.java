package antifraud.domain.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter
public class TransactionRequest {
   @NotEmpty
   @JsonProperty(required = true)
   private Long amount;
}
