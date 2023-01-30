package com.example.antifraud.service;


import com.example.antifraud.model.entities.UserEntity;

import com.example.antifraud.model.dto.UserResponse;
import com.example.antifraud.model.enums.LockStatus;
import com.example.antifraud.model.enums.Role;
import com.example.antifraud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

    @Transactional
    @Override
    public List<UserResponse> getListOfUsers() {
        List<UserResponse> users = new ArrayList<>();
        userRepository.findAll(Sort.by("id"))
                .forEach(userEntity -> users.add(
                        new UserResponse(userEntity)
                ));
        return users;
    }

    @Override
    @Transactional
    public Optional<UserResponse> createUser(UserEntity userEntity) {
        if (userRepository.existsByUsernameIgnoreCase(userEntity.getUsername())) {
            return Optional.empty();
        }

        if (userRepository.countBy() == 0) {
            userEntity.setRole(Role.ADMINISTRATOR);
            userEntity.setLocked(false);
            userEntity.setPassword(passwordEncoder.encode((userEntity.getPassword())));
            userRepository.save(userEntity);
            return Optional.of(new UserResponse(userEntity));
        }

        userEntity.setRole(Role.MERCHANT);
        userEntity.setLocked(true);
        userEntity.setPassword(passwordEncoder.encode((userEntity.getPassword())));

        userRepository.save(userEntity);
        return Optional.of(new UserResponse(userEntity));
    }

    @Transactional
    @Override
    public boolean delete(String username) {
        int result = userRepository.deleteByUsernameIgnoreCase(username);
        return result == 1;
    }

    @Transactional
    @Override
    public UserResponse update(Role role, String username) {
        if (role != Role.SUPPORT && role != Role.MERCHANT) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        UserEntity user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (user.getRole() == role) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        user.setRole(role);
        userRepository.save(user);

        return new UserResponse(user);
    }


    @Transactional
    @Override
    public void changeLock(String username, LockStatus operation) {
        var user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (user.getRole() == Role.ADMINISTRATOR) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (operation == LockStatus.LOCK) {
            user.setLocked(true);
        }
        if (operation == LockStatus.UNLOCK) {
            user.setLocked(false);
        }

        userRepository.save(user);
    }
}
