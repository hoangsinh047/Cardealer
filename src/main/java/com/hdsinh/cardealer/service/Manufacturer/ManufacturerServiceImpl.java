package com.hdsinh.cardealer.service.Manufacturer;

import com.hdsinh.cardealer.entities.Manufacturer;
import com.hdsinh.cardealer.repository.Manufacturer.ManufacturerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ManufacturerServiceImpl implements ManufacturerService {
    @Autowired
    private ManufacturerRepository manufacturerRepository;

    public Manufacturer addManufacturer(String name) {
        if (manufacturerRepository.existsByName(name)) {
            throw new RuntimeException("Hãng xe này đã tồn tại!");
        }
        Manufacturer manufacturer = new Manufacturer(name);
        return manufacturerRepository.save(manufacturer);
    }

    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    public Manufacturer save(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    public boolean delete(Long id) {
        Optional<Manufacturer> manufacturer = manufacturerRepository.findById(id);
        if (manufacturer.isPresent()) {
            manufacturerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
