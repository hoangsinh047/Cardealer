package com.hdsinh.cardealer.service.Manufacturer;

import com.hdsinh.cardealer.entities.Manufacturer;

import java.util.List;

public interface ManufacturerService {
    List<Manufacturer> getAllManufacturers();
    Manufacturer addManufacturer(String name);
    Manufacturer save(Manufacturer manufacturer);
    boolean delete(Long id);
}
