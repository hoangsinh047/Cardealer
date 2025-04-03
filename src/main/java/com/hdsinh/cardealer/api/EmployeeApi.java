package com.hdsinh.cardealer.api;

import com.hdsinh.cardealer.dto.ObjectDto;
import com.hdsinh.cardealer.entities.Employee;
import com.hdsinh.cardealer.entities.Manufacturer;
import com.hdsinh.cardealer.entities.Product;
import com.hdsinh.cardealer.repository.Employee.EmployeeRepository;
import com.hdsinh.cardealer.repository.Employee.EmployeeRepositoryImpl;
import com.hdsinh.cardealer.services.Employee.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/employee")
@Slf4j
public class EmployeeApi {
    private final EmployeeService employeeService;

    public EmployeeApi(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/getAllEmployee")
    public ResponseEntity<ObjectDto> getList(
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "start", required = false) Integer start,
            @RequestParam(name = "total", required = false) Integer total) {

        ObjectDto objectDto = null;
        try {
            objectDto = employeeService.loadAll(search, start, total);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(objectDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(objectDto, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee(
            @RequestParam("code") String code,
            @RequestParam("tenNhanVien") String name,
            @RequestParam("email") String email,
            @RequestParam("address") String address,
            @RequestParam("phoneNumber") Integer phone,
            @RequestParam("sex") String sex,
            @RequestParam("birthDay") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date birth,
            @RequestParam("position") String position,
            @RequestParam("degree") String degree) {

        // Tạo đối tượng sản phẩm
        Employee employee = new Employee();
        employee.setCode(code);
        employee.setName(name);
        employee.setEmail(email);
        employee.setAddress(address);
        employee.setPhone(phone);
        employee.setSex(sex);
        employee.setBirth(birth);
        employee.setCreatedDate(LocalDateTime.now());
        employee.setPosition(position);
        employee.setDegree(degree);

        // Lưu sản phẩm vào DB
        Employee savedEmployee = employeeService.save(employee);

        return ResponseEntity.ok(savedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        employeeService.delete(id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }
}
