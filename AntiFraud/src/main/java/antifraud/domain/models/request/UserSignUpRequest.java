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
    private String name;
    @NotEmpty
    @JsonProperty(value="username")
    private String userName;
    @NotEmpty
    private String password;
}
