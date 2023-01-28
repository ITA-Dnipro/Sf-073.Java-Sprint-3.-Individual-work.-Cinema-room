package antifraud.rest.dto;

import antifraud.domain.model.User;
import antifraud.domain.model.UserFactory;
import antifraud.domain.model.enums.UserRole;
import lombok.Builder;

import javax.validation.constraints.NotEmpty;

@Builder
public record UserRoleDTO(@NotEmpty
                          String username,
                          @NotEmpty
                          String role) {

    public User toModel() {
        return UserFactory.createWithRole(username, UserRole.valueOf(role));
    }
}