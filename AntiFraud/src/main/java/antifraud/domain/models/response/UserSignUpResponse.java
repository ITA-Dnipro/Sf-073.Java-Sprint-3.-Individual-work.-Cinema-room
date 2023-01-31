package antifraud.domain.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class UserSignUpResponse implements Serializable {
    private static final long serialVersionUID = -6575188705866757166L;
    @JsonProperty("id")
    private Long id;
    private String name;
    @NotEmpty
    @JsonProperty("username")
    private String userName;
}
