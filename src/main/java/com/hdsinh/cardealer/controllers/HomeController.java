package com.hdsinh.cardealer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model) {
        return "client/index";
    }

    @GetMapping("/wishlist")
    public String wishlist(Model model) {
        return "client/wishlist";
    }
}

