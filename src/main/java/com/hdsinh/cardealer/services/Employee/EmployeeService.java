package com.hdsinh.cardealer.services.Employee;

import com.hdsinh.cardealer.dto.ObjectDto;
import com.hdsinh.cardealer.entities.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();

    ObjectDto loadAll(String search, Integer start, Integer total);
    Employee save(Employee employee);
}
