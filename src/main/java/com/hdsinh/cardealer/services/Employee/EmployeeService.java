package com.hdsinh.cardealer.services.Employee;

import com.hdsinh.cardealer.dto.ObjectDto;
import com.hdsinh.cardealer.entities.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> findAll();
    String generateEmployeeCode();
    Employee getEmployeeById(Long id);
    ObjectDto loadAll(String search, Integer start, Integer total);
    Employee save(Employee employee);
    boolean delete(Long id);
    Optional<Employee> findById(Long id);
}
