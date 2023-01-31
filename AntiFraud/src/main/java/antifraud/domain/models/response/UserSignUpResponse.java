package antifraud.domain.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@JsonPropertyOrder({"id", "name", "username", "role"})
public class UserSignUpResponse implements Serializable {
    private static final long serialVersionUID = -6575188705866757166L;
    @JsonProperty("id")
    private Long id;
    private String name;
    @JsonProperty("username")
    private String userName;

    private String role;


}
