package com.hdsinh.cardealer.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "NAME", nullable = false)
    private String name;

    @Basic
    @Column(name = "STATUS", nullable = false)
    private String status;

    @Basic
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "manufacturer_id", nullable = false)
    private Long manufacturerId;

    @Basic
    @Column(name = "GEARBOX", nullable = false)
    private String gearbox;

    @Basic
    @Column(name = "FUEL", nullable = false)
    private String fuel;

    @Basic
    @Column(name = "COLOR", nullable = false)
    private String color;

    @Basic
    @Column(name = "IMAGE_URL", nullable = true)
    private byte[] imageUrl;

    @Basic
    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;

    @Basic
    @Column(name = "ODO", nullable = false)
    private Integer odo;

    @Basic
    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;
}
