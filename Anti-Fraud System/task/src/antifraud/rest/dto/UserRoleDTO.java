package antifraud.rest.dto;

import antifraud.domain.model.User;
import antifraud.domain.model.UserFactory;
import antifraud.domain.model.enums.UserRole;
import antifraud.exceptions.NonExistentRoleException;
import lombok.Builder;

import javax.validation.constraints.NotEmpty;
import java.util.Arrays;

@Builder
public record UserRoleDTO(@NotEmpty
                          String username,
                          @NotEmpty
                          String role) {

    public static void checkIfRoleExists(String role) {
        boolean doesExist = Arrays.stream(UserRole.values())
                .anyMatch(r -> r.name().equals(role));
        if (!doesExist) {
            throw new NonExistentRoleException();
        }
    }

    public User toModel() {
        return UserFactory.createWithRole(username, UserRole.valueOf(role));
    }
}