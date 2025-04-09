package com.hdsinh.cardealer.api;

import com.hdsinh.cardealer.dto.ObjectDto;
import com.hdsinh.cardealer.entities.Customer;
import com.hdsinh.cardealer.services.Customer.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/customer")
@Slf4j
public class CustomerApi {

    private final CustomerService customerService;

    @Autowired
    public CustomerApi(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/getAllCustomer")
    public ResponseEntity<ObjectDto> getList(
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "start", required = false) Integer start,
            @RequestParam(name = "total", required = false) Integer total) {

        ObjectDto objectDto = null;
        try {
            objectDto = customerService.loadAll(search, start, total);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(objectDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(objectDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        customerService.delete(id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<Customer> addCustomer(
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "tenKhachhang", required = false) String name,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phoneNumber", required = false) Integer phone,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "tenSanPham", required = false) Long productId) {

        // Tạo đối tượng sản phẩm
        Customer customer = new Customer();
        customer.setCode(code);
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setContent(content);
        customer.setProductId(productId);

        // Lưu sản phẩm vào DB
        Customer savedCustomer = customerService.save(customer);

        return ResponseEntity.ok(savedCustomer);
    }
}
