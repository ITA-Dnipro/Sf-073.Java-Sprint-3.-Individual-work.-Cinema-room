package antifraud.rest.dto;

import antifraud.domain.model.User;
import antifraud.domain.model.UserFactory;
import antifraud.domain.model.constants.UserAccess;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import javax.validation.constraints.NotEmpty;

@Builder
public record UserAccessDTO(@NotEmpty
                            @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
                            String username,
                            @NotEmpty
                            @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
                            UserAccess operation,
                            @JsonProperty(access = JsonProperty.Access.READ_ONLY)
                            String status) {
    public static UserAccessDTO fromModel(User userPermission) {
        String customMessage = String.format("User %s %sed!",
                userPermission.getUsername(), userPermission.getAccess());

        return UserAccessDTO.builder()
                .status(customMessage)
                .build();
    }

    public User toModel() {
        return UserFactory.createWithAccess(username, operation);
    }
}