package com.hdsinh.cardealer.services;

import com.hdsinh.cardealer.entities.Products;
import com.hdsinh.cardealer.repository.products.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Products> getAllProduct() {
        return productRepository.findAll();
    }

    public Products saveProduct(Products products) {
        return productRepository.save(products);
    }
}