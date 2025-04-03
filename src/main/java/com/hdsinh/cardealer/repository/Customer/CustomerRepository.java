package com.hdsinh.cardealer.repository.Customer;

import com.hdsinh.cardealer.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long>, CustomerRepositoryCustom {
}
