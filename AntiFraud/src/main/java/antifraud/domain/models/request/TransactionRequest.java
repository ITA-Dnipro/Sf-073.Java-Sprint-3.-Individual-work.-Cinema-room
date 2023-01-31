package antifraud.domain.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter
public class TransactionRequest implements Serializable {
   private static final long serialVersionUID = -860911804707001868L;
   @NotEmpty
   @JsonProperty(required = true)
   private Long amount;
}
