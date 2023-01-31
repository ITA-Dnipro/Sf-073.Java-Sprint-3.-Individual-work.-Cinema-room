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
@Setter@Getter
public class UserSignUpRequest implements Serializable {

    private static final long serialVersionUID = -8046865786614086074L;
    @NotEmpty
    @JsonProperty(required = true)
    private String name;
    @NotEmpty
    @JsonProperty(value="username", required = true)
    private String userName;
    @NotEmpty
    @JsonProperty(required = true)
    private String password;

    @NotEmpty
    @JsonProperty(required = true)
    private String role;
}
