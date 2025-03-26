package com.hdsinh.cardealer.dto;


import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDto {
    private Integer id;
    private String name;
    private String status;
    private String description;
    private String manufacturer_id;
    private String gearbox;
    private String fuel;
    private String color;
    private BigDecimal price;
    private Integer quantity;

    public ProductDto() {

    }

    @Lob // Chỉ định đây là dữ liệu lớn (Large Object)
    private byte[] image; // Lưu ảnh dưới dạng mảng byte
}
