package com.hdsinh.cardealer.controllers;

import com.hdsinh.cardealer.entities.Products;
import com.hdsinh.cardealer.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class AdminController {

    private final ProductService productService;

    @GetMapping("/home")
    public String home(Model model) {
        return "admin/home";
    }

    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/Sanpham")
    public String quanlySanpham(Model model) {
        List<Products> product = productService.getAllProducts();
        model.addAttribute("products", product);
        return "admin/quanlySanpham";
    }

    @GetMapping("/Sanpham/New")
    public String addSanpham() {
        return "admin/addSanpham";
    }

    @PostMapping("/Sanpham")
    public String saveProduct(@ModelAttribute("product") Products product,
                              @RequestParam("ImageUpload") MultipartFile imageUpload) {
        productService.saveProduct(product);
        return "redirect:/admin/quanlySanpham";
    }
}
