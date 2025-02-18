package com.hdsinh.cardealer.services;

import com.hdsinh.cardealer.entities.Products;
import com.hdsinh.cardealer.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Products> getAllProduct() {
        return productRepository.findAll();
    }

    public Products saveProduct(Products products) {
        return productRepository.save(products);
    }
}