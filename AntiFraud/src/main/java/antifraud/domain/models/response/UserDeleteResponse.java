package antifraud.domain.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class UserDeleteResponse implements Serializable {
    private static final long serialVersionUID = 3055004851503946027L;
    @JsonProperty(value = "username")
    private String userName;

    private String status;
}
