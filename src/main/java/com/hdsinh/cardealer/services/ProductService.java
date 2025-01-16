package com.hdsinh.cardealer.services;

import com.hdsinh.cardealer.entities.Products;
import com.hdsinh.cardealer.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Products> getProductById(int id) {
        return productRepository.findById(id);
    }

    public List<Products> searchProductByName(String name) {
        return productRepository.findByNameContaining(name);
    }

    public List<Products> searchProductsByManufacturer (String manufacturer) {
        return productRepository.findByManufacturer(manufacturer);
    }
}
