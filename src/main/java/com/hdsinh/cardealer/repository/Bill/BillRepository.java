package com.hdsinh.cardealer.repository.Bill;

import com.hdsinh.cardealer.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BillRepository extends JpaRepository<Bill, Integer>, BillRepositoryCustom {
    @Query("SELECT COALESCE(SUM(b.price), 0) FROM Bill b")
    Double getTotalRevenue();

}
