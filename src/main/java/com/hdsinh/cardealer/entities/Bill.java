package com.hdsinh.cardealer.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "sale")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "CODE")
    private String code;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "product_id")
    private Long productId;

    @Basic
    @Column(name = "employee_id")
    private Long employeeId;

    @Basic
    @Column(name = "customer_id")
    private Long customerId;

    @Basic
    @Column(name = "phone")
    private String phone;

    @Basic
    @Column(name = "address")
    private String address;

    @Basic
    @Column(name = "quantity")
    private Integer quantity;

    @Basic
    @Column(name = "created_date")
    private LocalDate createdDate;

    @Basic
    @Column(name = "price")
    private BigDecimal price;

    @Transient
    private String productName;

    @Transient
    private String employeeName;

}
