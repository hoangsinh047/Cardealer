package com.hdsinh.cardealer.services;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.hdsinh.cardealer.entities.Users;
import com.hdsinh.cardealer.entities.User_Role;
import com.hdsinh.cardealer.security.CustomUserDetails;

@Service
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;
//    private final PasswordEncoder passwordEncoder;
    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Tìm người dùng trong cơ sở dữ liệu theo tên đăng nhập
        Users user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Không tìm thấy tài khoản: " + username);
        }


        System.out.println("User found: " + user.getUsername());
        System.out.println("User password from DB: " + user.getPassword());


//        Kiểm tra mật khẩu có khớp với mật khẩu trong DB (đã mã hóa)
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || authentication.getCredentials() == null) {
//            throw new BadCredentialsException("Không thể xác thực, thông tin đăng nhập không hợp lệ.");
//        }
//        String inputPassword = authentication.getCredentials().toString();
////        if (!passwordEncoder.matches(inputPassword, user.getPassword())) {
////            throw new BadCredentialsException("Mật khẩu không đúng");
////        }
//
//        if (!inputPassword.equals(user.getPassword())) {
//            throw new BadCredentialsException("Mật khẩu không đúng");
//        }

        Collection<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
        Set<User_Role> roles = user.getUserRoles();
        for (User_Role userRole : roles) {
            grantedAuthoritySet.add(new SimpleGrantedAuthority(userRole.getRole().getName()));
        }

        return new CustomUserDetails(user, grantedAuthoritySet);
    }

}
