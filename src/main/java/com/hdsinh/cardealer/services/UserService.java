package com.hdsinh.cardealer.services;

import com.hdsinh.cardealer.entities.Users;
import com.hdsinh.cardealer.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
