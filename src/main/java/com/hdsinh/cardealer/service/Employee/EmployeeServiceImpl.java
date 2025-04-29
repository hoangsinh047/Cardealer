package com.hdsinh.cardealer.service.Employee;

import com.hdsinh.cardealer.dto.ObjectDto;
import com.hdsinh.cardealer.entities.Employee;
import com.hdsinh.cardealer.repository.Employee.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAll() {
        return this.employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public String generateEmployeeCode() {
        Random random = new Random();
        int number = random.nextInt(10000); // Tạo số ngẫu nhiên từ 0 đến 9999
        return String.format("EM%04d", number); // Định dạng thành EMxxxx
    }

    @Override
    public ObjectDto loadAll(String search, Integer start, Integer total) {
        ObjectDto res = new ObjectDto();

        // Lấy danh sách từ repository
        List<Employee> list = employeeRepository.loadAll(search, start, total);

        // Lấy tổng số bản ghi thỏa điều kiện
        Long count = employeeRepository.countAll(search);

        // Cập nhật dữ liệu
        res.setData(list);
        res.setCount(count);

        return res;
    }

    public Employee save(Employee employee) {
        if (employee.getCode() == null || employee.getCode().isEmpty()) {
            employee.setCode(generateEmployeeCode());
        }
        return employeeRepository.save(employee);
    }


    public boolean delete(Long id) {
        Optional<Employee> product = employeeRepository.findById(id);
        if (product.isPresent()) {
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }
}
