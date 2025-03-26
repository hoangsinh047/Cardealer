package com.hdsinh.cardealer.repository.product;

import com.hdsinh.cardealer.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
}
