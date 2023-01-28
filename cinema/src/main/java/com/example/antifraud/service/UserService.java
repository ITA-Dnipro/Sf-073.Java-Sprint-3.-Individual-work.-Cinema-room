package com.example.antifraud.service;

import com.example.antifraud.model.UserEntity;
import com.example.antifraud.model.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    Optional<UserResponse> createUser(UserEntity userEntity);

    List<UserResponse> getListOfUsers();

    boolean delete(String username);
}
