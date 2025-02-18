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

    private final ProductService productService;

    public ShopController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/shop")
    public String showProduct(Model model) {
        List<Products> products = this.productService.getAllProducts();
        model.addAttribute("products", products);
        return "client/shop";
    }

    @GetMapping("/checkout")
    public String showCheckout(Model model) {
        return "client/checkout";
    }

    @GetMapping("/singleproduct")
    public String showSingleProduct(Model model) {
        return "client/singleproduct";
    }
}

