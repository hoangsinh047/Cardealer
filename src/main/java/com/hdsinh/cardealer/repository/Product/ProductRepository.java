package com.hdsinh.cardealer.repository.Product;

import com.hdsinh.cardealer.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    @Query("SELECT COUNT(p) FROM Product p WHERE p.quantity <= 1")
    Long countLowStockProducts();

    @Query("SELECT p FROM Product p WHERE (SELECT COUNT(b) FROM Bill b WHERE b.productId = p.id) >= 3")
    List<Product> findProductsWithBills();
}
