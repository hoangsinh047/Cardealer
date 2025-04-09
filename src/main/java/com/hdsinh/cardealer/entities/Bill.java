package com.hdsinh.cardealer.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
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
    @Column(name = "phone")
    private String phone;

    @Basic
    @Column(name = "employee_id")
    private Long employeeId;

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
