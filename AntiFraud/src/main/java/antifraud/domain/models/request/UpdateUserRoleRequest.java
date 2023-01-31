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
@Setter @Getter
public class UpdateUserRoleRequest implements Serializable {
    private static final long serialVersionUID = 8673679880872574654L;
    @NotEmpty
    @JsonProperty(value = "username", required = true)
    private String userName;
    @NotEmpty
    @JsonProperty(required = true)
    private String role;

}
