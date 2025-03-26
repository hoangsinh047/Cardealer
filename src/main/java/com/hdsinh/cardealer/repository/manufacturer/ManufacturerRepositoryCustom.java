package com.hdsinh.cardealer.repository.manufacturer;

import com.hdsinh.cardealer.entities.Manufacturer;
import com.hdsinh.cardealer.entities.Product;

import java.util.List;

public interface ManufacturerRepositoryCustom {

    List<Manufacturer> loadAll();

}
