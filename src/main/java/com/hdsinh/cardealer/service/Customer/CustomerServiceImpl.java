package com.hdsinh.cardealer.service.Customer;

import com.hdsinh.cardealer.dto.ObjectDto;
import com.hdsinh.cardealer.entities.Customer;
import com.hdsinh.cardealer.repository.Customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        return this.customerRepository.findAll();
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
        List<Customer> list = customerRepository.loadAll(search, start, total);

        // Lấy tổng số bản ghi thỏa điều kiện
        Long count = customerRepository.countAll(search);

        // Cập nhật dữ liệu
        res.setData(list);
        res.setCount(count);

        return res;
    }

    public boolean delete(Long id) {
        Optional<Customer> product = customerRepository.findById(id);
        if (product.isPresent()) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Customer save(Customer customer) {
        if (customer.getCode() == null || customer.getCode().isEmpty()) {
            customer.setCode(generateEmployeeCode());
        }
        return customerRepository.save(customer);
    }
}
