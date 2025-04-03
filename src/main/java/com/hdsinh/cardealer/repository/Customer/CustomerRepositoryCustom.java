package com.hdsinh.cardealer.repository.Customer;

import com.hdsinh.cardealer.entities.Customer;

import java.util.List;

public interface CustomerRepositoryCustom {
    List<Customer> loadAll(String search, Integer start, Integer total);
    Long countAll(String search);
}
