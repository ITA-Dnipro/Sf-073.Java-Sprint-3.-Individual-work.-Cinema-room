package com.example.antifraud.model.dto;

import com.example.antifraud.model.enums.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RoleRequest {
    @NotBlank
    String username;

    Role role;
}

