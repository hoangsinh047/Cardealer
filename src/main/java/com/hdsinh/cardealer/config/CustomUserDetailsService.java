package com.hdsinh.cardealer.config;

import com.hdsinh.cardealer.entities.Users;
import com.hdsinh.cardealer.repository.User.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        logger.info("Xu li username: {}", username);
//
//        // Lấy người dùng từ repository
//        Optional<Users> userOptional = userRepository.findByUsername(username);
//
//        // Nếu không tìm thấy người dùng, ném ngoại lệ
//        if (userOptional.isEmpty()) {
//            logger.warn("Khong tim thay nguoi dung: {}", username);
//            throw new UsernameNotFoundException("Không tìm thấy người dùng: " + username);
//        }
//
//        Users user = userOptional.get();
//
//        return org.springframework.security.core.userdetails.User.builder()
//                .username(user.getUsername())
//                .password(user.getPassword())
//                .build();
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // ✅ Gán employeeName từ liên kết đến employee
        if (user.getEmployee() != null) {
            user.setEmployeeName(user.getEmployee().getName());
        }

        return new CustomUserDetails(user); // hoặc return user nếu bạn implement UserDetails trong Users
    }



}
