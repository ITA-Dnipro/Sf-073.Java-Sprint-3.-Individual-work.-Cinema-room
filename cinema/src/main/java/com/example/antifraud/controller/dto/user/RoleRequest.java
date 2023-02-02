package com.example.antifraud.controller.dto.user;

import javax.validation.constraints.NotBlank;


import com.example.antifraud.model.enums.Role;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RoleRequest {
    @NotBlank
    String username;

    Role role;
}

