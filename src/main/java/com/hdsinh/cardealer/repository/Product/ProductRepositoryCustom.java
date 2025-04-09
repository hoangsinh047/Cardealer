package com.hdsinh.cardealer.repository.Product;

import com.hdsinh.cardealer.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> loadAll(String search, String status, Long manufacturerId,
                          String type, BigDecimal minPrice, BigDecimal maxPrice, Integer minOdo, Integer maxOdo, String fuel,
                          String gearbox, Integer start, Integer total);
    Long countAll(String search);
    List<Product> findOutOfStockProducts();

}
