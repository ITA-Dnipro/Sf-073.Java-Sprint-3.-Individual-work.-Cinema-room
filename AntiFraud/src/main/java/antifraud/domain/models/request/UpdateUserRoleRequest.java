package antifraud.domain.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class UpdateUserRoleRequest {
    @NotEmpty
    @JsonProperty(value = "username", required = true)
    private String userName;
    @NotEmpty
    @JsonProperty(required = true)
    private String role;

}
