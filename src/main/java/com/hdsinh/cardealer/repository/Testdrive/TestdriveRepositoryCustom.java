package com.hdsinh.cardealer.repository.Testdrive;

import com.hdsinh.cardealer.entities.Customer;
import com.hdsinh.cardealer.entities.Testdrive;

import java.util.List;

public interface TestdriveRepositoryCustom {
    List<Testdrive> loadAll(String search, Integer start, Integer total);
    Long countAll(String search);
}
