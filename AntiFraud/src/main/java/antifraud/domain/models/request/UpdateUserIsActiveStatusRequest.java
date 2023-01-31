package antifraud.domain.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter
public class UpdateUserIsActiveStatusRequest implements Serializable {
    private static final long serialVersionUID = -6654344180155249462L;

    @NotEmpty
    @JsonProperty(value = "username", required = true)
    private String userName;
    @NotEmpty
    @JsonProperty(required = true)
    private String operation;
}
