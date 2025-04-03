package com.hdsinh.cardealer.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
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
    @Column(name = "STATUS", nullable = false)
    private String status;

    @Basic
    @Lob
    @Column(name = "DESCRIPTION", nullable = false, columnDefinition = "LONGTEXT")
    private String description;

    @Basic
    @Column(name = "manufacturer_id", nullable = false)
    private Long manufacturerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id", insertable = false, updatable = false)
    private Manufacturer manufacturer;

    @Basic
    @Column(name = "GEARBOX", nullable = false)
    private String gearbox;

    @Basic
    @Column(name = "FUEL", nullable = false)
    private String fuel;

    @Basic
    @Column(name = "COLOR", nullable = false)
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
    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "first_regis", nullable = true)
    private LocalDate firstRegis;

    @Basic
    @Column(name = "num_seat", nullable = false)
    private Integer numSeat;

    @Transient
    private String manufacturerName;

}
