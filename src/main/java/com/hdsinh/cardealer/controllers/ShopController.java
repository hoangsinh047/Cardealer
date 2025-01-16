package com.hdsinh.cardealer.controllers;

import com.hdsinh.cardealer.entities.Products;
import com.hdsinh.cardealer.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import java.util.List;

@Controller
public class ShopController {
    @Autowired
    private ProductService productService;

    @GetMapping("/shop")
    public String showProduct(Model model) {
        try {
            List<Products> products = productService.getAllProducts();
            model.addAttribute("products", products);
            return "client/shop";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Loi roi");
            e.printStackTrace();
            return "404";
        }
    }
}

