package com.hdsinh.cardealer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "client/index";
    }

    @GetMapping("/wishlist")
    public String wishlist() {
        return "client/wishlist";
    }

    @GetMapping("/blog")
    public String blog() {
        return "client/blog";
    }

    @GetMapping("/myaccount")
    public String myaccount() {
        return "client/my-account";
    }

    @GetMapping("cart")
    public String cart() {
        return "client/cart";
    }
}

