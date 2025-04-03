package com.hdsinh.cardealer.services.Bill;

import com.hdsinh.cardealer.dto.ObjectDto;
import com.hdsinh.cardealer.entities.Bill;

import java.util.List;

public interface BillService {
    ObjectDto loadAll(String search, Integer start, Integer total);

    Bill save(Bill bill);

    List<Bill> findAll();

    Double getTotalRevenue();
}
