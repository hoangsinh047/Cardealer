package com.hdsinh.cardealer.service.Bill;

import com.hdsinh.cardealer.dto.ObjectDto;
import com.hdsinh.cardealer.entities.Bill;
import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.util.List;

public interface BillService {
    ObjectDto loadAll(String search, Integer start, Integer total);

    Bill save(Bill bill);

    List<Bill> findAll();

    Double getTotalRevenue();

    Bill findById(Long id);

    byte[] generateInvoicePdf(Long id) throws DocumentException, IOException;
}