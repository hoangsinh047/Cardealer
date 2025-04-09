package com.hdsinh.cardealer.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Entity
@Table(name = "test_drive")
public class Testdrive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "CODE")
    private String code;

    @Basic
    @Column(name = "FULL_NAME", nullable = false)
    private String name;

    @Basic
    @Column(name = "PHONE")
    private Integer phone;

    @Basic
    @Column(name = "product_id")
    private Long productId;

    @Basic
    @Column(name = "ADDRESS")
    private String address;

    @Basic
    @Column(name = "expected_date", nullable = true)
    private Date expectedDate;

    @Basic
    @Column(name = "driving_license")
    private String drivingLicense;

    @Transient
    private String productName;
}
