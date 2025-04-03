package com.hdsinh.cardealer.repository.Bill;

import com.hdsinh.cardealer.dto.BillDto;


import java.util.List;

public interface BillRepositoryCustom {
    List<BillDto> loadAll(String search, Integer start, Integer total);

    Long countAll(String search);
}
