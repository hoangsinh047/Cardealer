package com.hdsinh.cardealer.service.User;

import com.hdsinh.cardealer.dto.BillDto;
import com.hdsinh.cardealer.dto.ObjectDto;
import com.hdsinh.cardealer.entities.*;
import com.hdsinh.cardealer.repository.Employee.EmployeeRepository;
import com.hdsinh.cardealer.repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private EmployeeRepository employeeRepository;

    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<Users> findAll() {
        List<Users> user = userRepository.findAll();

        for (Users users : user) {
            if (users.getEmployeeId() != null) {
                Employee employee = employeeRepository.findById(users.getEmployeeId()).orElse(null);
                if (employee != null) {
                    users.setEmployeeName(employee.getName()); // Giả sử Product có field name
                }
            }
        }
        return this.userRepository.findAll();
    }

    public ObjectDto loadAll(String search, Integer start, Integer total) {
        ObjectDto res = new ObjectDto();

        // Lấy danh sách từ repository
        List<Users> list = userRepository.loadAll(search, start, total);

        // Lấy tổng số bản ghi thỏa điều kiện
        Long count = userRepository.countAll(search);

        // Cập nhật dữ liệu
        res.setData(list);
        res.setCount(count);

        return res;
    }

    public List<Users> getAllUser() {
        return userRepository.findAll();
    }

    public Users save(Users user) {
        // Kiểm tra nếu password chưa được mã hóa
        if (user.getPassword() != null && !user.getPassword().startsWith("$2a$")) {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
        }
        return userRepository.save(user);
    }

    public boolean delete(Long id) {
        Optional<Users> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
