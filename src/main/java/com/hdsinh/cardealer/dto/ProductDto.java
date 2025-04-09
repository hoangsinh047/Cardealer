package com.hdsinh.cardealer.dto;

import com.hdsinh.cardealer.entities.Product;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Data
@Getter
@Setter
public class ProductDto {
    private Long id;
    private String code;
    private String name;
    private String status;
    private String description;
    private String manufacturerName;
    private String gearbox;
    private String fuel;
    private String color;
    private BigDecimal price;
    private Integer quantity;
    private String type;
    private String odo;
    private LocalDate firstRegis;
    private Integer numSeat;
    private List<String> images;
    private Long totalSoldQuantity;


    public ProductDto() {
    }

    public ProductDto(Product product) {
        this.id = product.getId();
        this.code = product.getCode();
        this.name = product.getName();
        this.status = product.getStatus();
        this.description = product.getDescription();
        this.manufacturerName = product.getManufacturer().getName();
        this.gearbox = product.getGearbox();
        this.fuel = product.getFuel();
        this.color = product.getColor();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.type = product.getType();
        this.odo = product.getOdo();
        this.firstRegis = product.getFirstRegis();
        this.numSeat = product.getNumSeat();
        this.images = Arrays.asList(product.getImageUrl().split(","));
    }

    public ProductDto(Product product, Long totalQuantity) {
        this.id = product.getId();
        this.code = product.getCode();
        this.name = product.getName();
        this.manufacturerName = product.getManufacturer().getName();
        this.price = product.getPrice();
        this.totalSoldQuantity = totalQuantity;

    }

}
