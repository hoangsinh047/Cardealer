package com.hdsinh.cardealer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/shop")
public class ShopController {
    @RequestMapping("/")
    public String shoptohome() {
        return "client/index";
    }

    @RequestMapping("/index")
    public String backhome() {
        return "client/index";
    }

    @RequestMapping("/login")
    public String login() {
        return "client/login";
    }

    @RequestMapping("/shop")
    public String shop() {
        return "client/shop-fullwidth";
    }

    @RequestMapping("/singleproduct")
    public String singleproduct() {
        return "client/single-product";
    }

    @RequestMapping("/blog")
    public String blog() {
        return "client/blog-details";
    }

    @RequestMapping("/contact")
    public String contact() {
        return "client/contact";
    }
}

