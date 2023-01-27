package com.example.antifraud.controller;

import com.example.antifraud.model.UserEntity;

import com.example.antifraud.service.UserServiceImpl;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/auth")
@Validated
@AllArgsConstructor
public class UserController {
    UserServiceImpl userService;

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserEntity createUser(@Valid @RequestBody UserEntity userEntity){
        return userService.createUser(userEntity)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.CONFLICT));
    }
}
