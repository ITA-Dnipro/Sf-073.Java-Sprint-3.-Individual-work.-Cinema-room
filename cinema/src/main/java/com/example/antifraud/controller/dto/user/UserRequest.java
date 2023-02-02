package com.example.antifraud.controller.dto.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    Long id;
    String name;
    String username;
}
