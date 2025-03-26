package com.hdsinh.cardealer.api;

import com.hdsinh.cardealer.entities.Manufacturer;
import com.hdsinh.cardealer.services.Manufacturer.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
