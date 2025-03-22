package com.hdsinh.cardealer.services.Product;

import com.hdsinh.cardealer.dto.ObjectDto;
import com.hdsinh.cardealer.entities.Employee;
import com.hdsinh.cardealer.entities.Products;

import java.util.List;

public interface ProductService {
    List<Products> findAll();

    ObjectDto loadAll(String search, Integer start, Integer total);
}
