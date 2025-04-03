package com.hdsinh.cardealer.api;

import com.hdsinh.cardealer.entities.Manufacturer;
import com.hdsinh.cardealer.services.Manufacturer.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Manufacturer> addManufacturer(@RequestParam("tenHangXe") String name) {
        Manufacturer savedManufacturer = manufacturerService.addManufacturer(name);
        return ResponseEntity.ok(savedManufacturer);
    }

}
