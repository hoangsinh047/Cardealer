package com.hdsinh.cardealer.repository.Employee;

import com.hdsinh.cardealer.entities.Employee;

import java.util.List;

public interface EmployeeRepositoryCustom {
    List<Employee> loadAll(String search, Integer start, Integer total);

    Long countAll(String search);
}
