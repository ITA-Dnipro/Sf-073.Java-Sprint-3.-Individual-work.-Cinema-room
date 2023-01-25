package antifraud.rest.dto;

import antifraud.domain.model.User;
import antifraud.domain.model.UserFactory;
import antifraud.domain.model.constants.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import javax.validation.constraints.NotEmpty;

@Builder
public record UserRoleDTO(@JsonProperty(access = JsonProperty.Access.READ_ONLY)
                          Long id,
                          @JsonProperty(access = JsonProperty.Access.READ_ONLY)
                          String name,
                          @NotEmpty
                          @JsonProperty(access = JsonProperty.Access.READ_WRITE)
                          String username,
                          @NotEmpty
                          @JsonProperty(access = JsonProperty.Access.READ_WRITE)
                          UserRole role) {
    public static UserRoleDTO fromModel(User updatedUser) {
        return UserRoleDTO.builder()
                .id(updatedUser.getId())
                .name(updatedUser.getName())
                .username(updatedUser.getUsername())
                .role(updatedUser.getRole())
                .build();
    }

    public User toModel() {
        return UserFactory.createWithRole(username, role);
    }
}