package com.hdsinh.cardealer.api;

import com.hdsinh.cardealer.entities.Employee;
import com.hdsinh.cardealer.entities.Manufacturer;
import com.hdsinh.cardealer.services.Manufacturer.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/manufacturers")
@CrossOrigin(origins = "*") // Cho phép gọi từ frontend
public class ManufacturerApi {
    @Autowired
    private ManufacturerService manufacturerService;

    @GetMapping("/getList")
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerService.getAllManufacturers();
    }

    @PostMapping("/add")
    public ResponseEntity<Manufacturer> addBrand(
            @RequestParam(value = "tenHangXe" ,required = false) String name) {

        // Tạo đối tượng sản phẩm
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(name);;

        // Lưu sản phẩm vào DB
        Manufacturer savedBrand = manufacturerService.save(manufacturer);

        return ResponseEntity.ok(savedBrand);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBrand(@PathVariable Long id) {
        manufacturerService.delete(id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }
}
