package com.hdsinh.cardealer.dto;


import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDto {
    private Long id;
    private String name;
    private String status;
    private String description;
    private String manufacturerName;
    private String gearbox;
    private String fuel;
    private String color;
    private BigDecimal price;
    private Integer quantity;
    private String imageUrl;

    public ProductDto(Long id, String name, String status, String description, String manufacturerName, String gearbox,
                      String fuel, String color, BigDecimal price, Integer quantity) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.description = description;
        this.manufacturerName = manufacturerName;
        this.gearbox = gearbox;
        this.fuel = fuel;
        this.color = color;
        this.price = price;
        this.quantity = quantity;
    }
}
