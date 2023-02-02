package com.example.antifraud.controller.dto.user;


import com.example.antifraud.model.enums.LockStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LockStatusRequest {
    @NotBlank
    String username;
    LockStatus operation;
}