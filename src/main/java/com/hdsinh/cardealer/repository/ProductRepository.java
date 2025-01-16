package com.hdsinh.cardealer.repository;

import com.hdsinh.cardealer.entities.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products, Integer> {
    List<Products> findByNameContaining(String name);
    List<Products> findByManufacturer(String manufacturer);
}
