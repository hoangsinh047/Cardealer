package com.hdsinh.cardealer.repository.Bill;

import com.hdsinh.cardealer.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long>, BillRepositoryCustom {
    @Query("SELECT COALESCE(SUM(b.price), 0) FROM Bill b")
    Double getTotalRevenue();

    @Query("SELECT b.productId, SUM(b.quantity) FROM Bill b GROUP BY b.productId ORDER BY SUM(b.quantity) DESC")
    List<Object[]> findTotalQuantitySoldForProducts();

}
