package antifraud.domain.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter
public class TransactionResponse implements Serializable {
    private static final long serialVersionUID = -5887672263772302723L;
    private String result;

}
