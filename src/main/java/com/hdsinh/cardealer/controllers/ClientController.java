package com.hdsinh.cardealer.controllers;

import com.hdsinh.cardealer.entities.Manufacturer;
import com.hdsinh.cardealer.entities.Product;
import com.hdsinh.cardealer.services.Manufacturer.ManufacturerService;
import com.hdsinh.cardealer.services.Product.ProductService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/")
public class ClientController {
    @Autowired
    ProductService productService;

    @Autowired
    private ManufacturerService manufacturerService;

    @GetMapping
    public String home(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("activePage", "home");
        return "client/index";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("activePage", "contact");
        return "client/contact";
    }

    @GetMapping("/test-drive")
    public String testdrive(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("product", products);
        model.addAttribute("activePage", "test-drive");
        return "client/testdrive";
    }

    @GetMapping("/blog")
    public String blog(Model model) {
        model.addAttribute("activePage", "blog");
        return "client/blog";
    }

    @GetMapping("/blog-details")
    public String blogDetails(Model model) {
        model.addAttribute("activePage", "blog-details");
        return "client/blog-details";
    }
}

