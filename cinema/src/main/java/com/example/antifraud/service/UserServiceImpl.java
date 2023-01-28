package com.example.antifraud.service;


import com.example.antifraud.model.UserEntity;

import com.example.antifraud.model.UserResponse;
import com.example.antifraud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    @Lazy
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameIgnoreCase(username).orElseThrow(() ->
                new UsernameNotFoundException("User " + username + " not found"));
    }

    @Override
    public List<UserResponse> getListOfUsers() {
        List<UserResponse> users = new ArrayList<>();
        userRepository.findAll(Sort.by("id"))
                .forEach(userEntity -> users.add(
                        new UserResponse(
                                userEntity.getId(),
                                userEntity.getName(),
                                userEntity.getUsername()
                        )
                ));
        return users;
    }

    @Transactional
    public Optional<UserResponse> createUser(UserEntity userEntity) {
        if (userRepository.existsByUsernameIgnoreCase(userEntity.getUsername())) {
            return Optional.empty();
        }
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);

        return Optional.of(new UserResponse(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getUsername()
        ));
    }

    @Transactional
    @Override
    public boolean delete(String username) {
        int result = userRepository.deleteByUsernameIgnoreCase(username);
        return result == 1;
    }
}
