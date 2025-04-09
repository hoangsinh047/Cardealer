package com.hdsinh.cardealer.controllers;

import com.hdsinh.cardealer.dto.ProductDto;
import com.hdsinh.cardealer.entities.Manufacturer;
import com.hdsinh.cardealer.entities.Product;
import com.hdsinh.cardealer.services.Manufacturer.ManufacturerService;
import com.hdsinh.cardealer.services.Product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/cars")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private RestTemplate restTemplate;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String showCarsList(Model model) {
        List<Product> products = productService.getAllProducts();
        for (Product product : products) {
            if (product.getManufacturer() != null) {
                product.setManufacturerName(product.getManufacturer().getName());
            }
        }
        List<Manufacturer> manufacturers = manufacturerService.getAllManufacturers();
        model.addAttribute("manufacturer", manufacturers);
        model.addAttribute("products", products);
        model.addAttribute("activePage", "cars");
        return "client/cars";
    }

    @GetMapping("/{id}")
    public String showProductDetail(@PathVariable Long id, Model model) {
        String apiUrl = "http://localhost:8080/api/product/" + id;
        ProductDto productDto = restTemplate.getForObject(apiUrl, ProductDto.class);
        model.addAttribute("activePage", "cars");
        model.addAttribute("products", productDto);
        return "client/car-details";
    }
}
