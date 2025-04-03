package com.hdsinh.cardealer.services.Testdrive;

import com.hdsinh.cardealer.dto.ObjectDto;
import com.hdsinh.cardealer.entities.Testdrive;

import java.util.List;

public interface TestdriveService {
    List<Testdrive> findAll();
    ObjectDto loadAll(String search, Integer start, Integer total);
    boolean delete(Long id);
    Testdrive save(Testdrive testDrive);
}
