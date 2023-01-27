package com.example.antifraud.service;

import com.example.antifraud.model.UserEntity;

import com.example.antifraud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired @Lazy
    PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameIgnoreCase(username).orElseThrow(() ->
                new UsernameNotFoundException("User " + username + " not found"));
    }

    @Transactional
    public Optional<UserEntity> createUser(UserEntity userEntity) {
        if(userRepository.existsByUsernameIgnoreCase(userEntity.getUsername())) {
            return Optional.empty();
        }
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        return Optional.of(userRepository.save(userEntity));
    }
}
