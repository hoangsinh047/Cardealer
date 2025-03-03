package com.hdsinh.cardealer.repository.products;

import com.hdsinh.cardealer.entities.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Products, Long> {
}
