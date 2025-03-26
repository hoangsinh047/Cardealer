package com.hdsinh.cardealer.repository.Employee;

import com.hdsinh.cardealer.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, EmployeeRepositoryCustom {

}
