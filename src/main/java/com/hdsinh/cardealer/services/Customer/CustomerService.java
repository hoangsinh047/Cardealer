package com.hdsinh.cardealer.services.Customer;

import com.hdsinh.cardealer.dto.ObjectDto;
import com.hdsinh.cardealer.entities.Customer;

import java.util.List;

public interface CustomerService {
    ObjectDto loadAll(String search, Integer start, Integer total);
    boolean delete(Long id);
    List<Customer> findAll();
    Customer save(Customer customer);
}
