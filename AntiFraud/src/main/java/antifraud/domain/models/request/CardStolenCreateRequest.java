package antifraud.domain.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.LuhnCheck;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class CardStolenCreateRequest implements Serializable {

    private static final long serialVersionUID = 1456364523796207927L;
    @JsonProperty(required = true)
    @LuhnCheck()
    private String number;
}
