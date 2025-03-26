package com.hdsinh.cardealer.repository.product;

import com.hdsinh.cardealer.entities.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> loadAll(String search, Integer start, Integer total);

    Long countAll(String search);
}
