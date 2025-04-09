package com.hdsinh.cardealer.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "CODE")
    private String code;

    @Basic
    @Column(name = "NAME", nullable = false)
    private String name;

    @Basic
    @Column(name = "STATUS")
    private String status;

    @Basic
    @Lob
    @Column(name = "DESCRIPTION", columnDefinition = "LONGTEXT")
    private String description;

    @Basic
    @Column(name = "manufacturer_id")
    private Long manufacturerId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id", insertable = false, updatable = false)
    private Manufacturer manufacturer;

    @Basic
    @Column(name = "GEARBOX")
    private String gearbox;

    @Basic
    @Column(name = "FUEL")
    private String fuel;

    @Basic
    @Column(name = "COLOR")
    private String color;

    @Column(name = "IMAGE_URL", nullable = true, columnDefinition = "LONGTEXT")
    private String imageUrl;

    @Basic
    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;

    @Basic
    @Column(name = "TYPE")
    private String type;

    @Basic
    @Column(name = "ODO")
    private String odo;

    @Basic
    @Column(name = "QUANTITY")
    private Integer quantity;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "first_regis", nullable = true)
    private LocalDate firstRegis;

    @Basic
    @Column(name = "num_seat")
    private Integer numSeat;

    @Transient
    private String manufacturerName;

}
