package antifraud.rest.dto;


import antifraud.domain.model.User;
import antifraud.domain.model.UserFactory;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.constraints.NotEmpty;

@JsonPropertyOrder({"id", "name", "userName", "password"})
public record UserDTO(@JsonProperty(access = JsonProperty.Access.READ_ONLY)
                      Long id,
                      @NotEmpty
                      String name,
                      @NotEmpty
                      @JsonProperty("username")
                      String userName,
                      @NotEmpty
                      @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
                      String password) {
    public static UserDTO fromModel(User registeredUser) {
        return new UserDTO(registeredUser.getId(),
                registeredUser.getName(),
                registeredUser.getUserName(),
                "");
    }

    public User toModel() {
        return UserFactory.create(name, userName, password);
    }
}