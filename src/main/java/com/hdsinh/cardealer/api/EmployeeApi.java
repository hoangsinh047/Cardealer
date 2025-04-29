package com.hdsinh.cardealer.api;

import com.hdsinh.cardealer.dto.ObjectDto;
import com.hdsinh.cardealer.entities.Employee;
import com.hdsinh.cardealer.service.Employee.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "tenNhanVien", required = false) String name,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "phoneNumber", required = false) Integer phone,
            @RequestParam(value = "sex", required = false) String sex,
            @RequestParam(value = "birthDay", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date birth,
            @RequestParam(value = "position", required = false) String position,
            @RequestParam(value = "degree", required = false) String degree) {

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

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateEmployee(@PathVariable("id") Long id, @RequestBody Employee updatedEmployee) {
        Optional<Employee> employeeOptional = employeeService.findById(id);
        if (!employeeOptional.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "fail");
            response.put("message", "Employee not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        Employee existingEmployee = employeeOptional.get();
        existingEmployee.setCode(updatedEmployee.getCode());
        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setPhone(updatedEmployee.getPhone());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setBirth(updatedEmployee.getBirth());
        existingEmployee.setSex(updatedEmployee.getSex());
        existingEmployee.setPosition(updatedEmployee.getPosition());
        employeeService.save(existingEmployee);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("employee", existingEmployee);
        return ResponseEntity.ok(response);
    }


}
