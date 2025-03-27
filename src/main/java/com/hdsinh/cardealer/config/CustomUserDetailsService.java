package com.hdsinh.cardealer.config;

import com.hdsinh.cardealer.entities.Users;
import com.hdsinh.cardealer.repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("Xu li username: {}", username);

        // Lấy người dùng từ repository
        Optional<Users> userOptional = userRepository.findByUsername(username);

        // Nếu không tìm thấy người dùng, ném ngoại lệ
        if (userOptional.isEmpty()) {
            logger.warn("Khong tim thay nguoi dung: {}", username);
            throw new UsernameNotFoundException("Không tìm thấy người dùng: " + username);
        }

        Users user = userOptional.get();

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }



}
