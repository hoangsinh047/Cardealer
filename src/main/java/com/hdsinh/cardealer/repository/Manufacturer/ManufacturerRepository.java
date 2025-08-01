package com.hdsinh.cardealer.repository.Manufacturer;

import com.hdsinh.cardealer.entities.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long>, ManufacturerRepositoryCustom {

}
