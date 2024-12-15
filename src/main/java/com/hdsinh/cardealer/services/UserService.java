package com.hdsinh.cardealer.services;

import com.hdsinh.cardealer.entities.Users;
import com.hdsinh.cardealer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Users findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
