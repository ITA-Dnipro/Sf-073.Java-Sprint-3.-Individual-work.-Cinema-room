package antifraud.rest.dto;


import antifraud.domain.model.User;
import antifraud.domain.model.UserFactory;
import antifraud.domain.model.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import javax.validation.constraints.NotBlank;

@Builder
public record UserDTO(@JsonProperty(access = JsonProperty.Access.READ_ONLY)
                      Long id,
                      @NotBlank
                      String name,
                      @NotBlank
                      String username,
                      @NotBlank
                      @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
                      String password,
                      @JsonProperty(access = JsonProperty.Access.READ_ONLY)
                      UserRole role) {
    public static UserDTO fromModel(User registeredUser) {
        return UserDTO.builder()
                .id(registeredUser.getId())
                .name(registeredUser.getName())
                .username(registeredUser.getUsername())
                .role(registeredUser.getRole())
                .build();
    }

    public User toModel() {
        return UserFactory.create(name, username, password);
    }
}