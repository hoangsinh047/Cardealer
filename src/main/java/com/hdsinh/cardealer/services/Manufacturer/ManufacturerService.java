package com.hdsinh.cardealer.services.Manufacturer;

import com.hdsinh.cardealer.entities.Manufacturer;

import java.util.List;

public interface ManufacturerService {
    List<Manufacturer> getAllManufacturers();
    Manufacturer addManufacturer(String name);
}
