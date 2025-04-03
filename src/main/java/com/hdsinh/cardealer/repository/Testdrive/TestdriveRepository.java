package com.hdsinh.cardealer.repository.Testdrive;

import com.hdsinh.cardealer.entities.Customer;
import com.hdsinh.cardealer.entities.Testdrive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestdriveRepository extends JpaRepository<Testdrive, Long>, TestdriveRepositoryCustom {
}
