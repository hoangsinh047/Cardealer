package com.hdsinh.cardealer.api;

import com.hdsinh.cardealer.entities.Products;
import com.hdsinh.cardealer.repository.products.ProductRepository;
import com.hdsinh.cardealer.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/san-pham")
public class SanPhamApi {
    private final ProductService productService;


    public SanPhamApi(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("getAllProduct")
    public List<Products> getProducts() {
        return productService.getAllProduct();
    }
}
