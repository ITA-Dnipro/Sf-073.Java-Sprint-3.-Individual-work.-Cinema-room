package com.example.antifraud.service;


import com.example.antifraud.model.entities.UserEntity;
import com.example.antifraud.model.dto.UserResponse;
import com.example.antifraud.model.enums.LockStatus;
import com.example.antifraud.model.enums.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    Optional<UserResponse> createUser(UserEntity userEntity);

    List<UserResponse> getListOfUsers();

    boolean delete(String username);


    void changeLock(String username, LockStatus operation);

    UserResponse update(Role role, String username);
}
