package com.example.antifraud.model.dto;

import com.example.antifraud.model.entities.UserEntity;
import com.example.antifraud.model.enums.Role;
import lombok.Value;


@Value
public class UserResponse {
    Long id;
    String name;
    String username;
    Role role;

    public UserResponse(UserEntity userEntity) {
        id = userEntity.getId();
        name = userEntity.getName();
        username = userEntity.getUsername();
        role = userEntity.getRole();
    }

}
