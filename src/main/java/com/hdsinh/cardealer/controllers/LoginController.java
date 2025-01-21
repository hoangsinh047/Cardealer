package com.hdsinh.cardealer.controllers;

import com.hdsinh.cardealer.entities.Users;
import com.hdsinh.cardealer.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        logger.info("GET method in showLoginForm");
        return "login";
    }

    @PostMapping("/login")
    public String processLoginForm(@RequestParam("username") String username,
                                   @RequestParam("password") String password,
                                   RedirectAttributes redirectAttributes,
                                   HttpSession session) {
        Optional<Users> userOptional = userService.findByUsername(username);

        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                session.setAttribute("user", user);
                return "redirect:/index";
            }
        }
        redirectAttributes.addFlashAttribute("error", true);
        return "redirect:/login";
    }

    @GetMapping("/index")
    public String showIndex() {
        return "client/index";
    }
}
