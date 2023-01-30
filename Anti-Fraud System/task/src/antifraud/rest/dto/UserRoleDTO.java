package antifraud.rest.dto;

import antifraud.domain.model.User;
import antifraud.domain.model.UserFactory;
import antifraud.domain.model.enums.UserRole;
import antifraud.validation.AvailableRole;
import lombok.Builder;

import javax.validation.constraints.NotBlank;

@Builder
public record UserRoleDTO(@NotBlank
                          String username,
                          @NotBlank
                          @AvailableRole
                          String role) {

    public User toModel() {
        return UserFactory.createWithRole(username,
                UserRole.valueOf(role));
    }
}