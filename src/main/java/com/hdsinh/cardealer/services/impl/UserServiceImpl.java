package com.hdsinh.cardealer.services.impl;

import com.hdsinh.cardealer.repository.UserRepository;
import com.hdsinh.cardealer.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import com.hdsinh.cardealer.entities.Users;
import org.springframework.stereotype.Repository;

@Repository
public class UserServiceImpl extends UserService {
    @Autowired
    private UserRepository userRepository;
}
