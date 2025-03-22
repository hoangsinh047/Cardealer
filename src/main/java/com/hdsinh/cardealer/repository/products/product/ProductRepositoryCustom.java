package com.hdsinh.cardealer.repository.products.product;

import com.hdsinh.cardealer.entities.Employee;
import com.hdsinh.cardealer.entities.Products;

import java.util.List;

public interface ProductRepositoryCustom {

    List<Products> loadAll(String search, Integer start, Integer total);

    Long countAll(String search);
}
