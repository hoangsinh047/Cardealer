package com.hdsinh.cardealer.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "CODE")
    private String code;

    @Basic
    @Column(name = "NAME")
    private String name;

    @Basic
    @Column(name = "PHONE")
    private Integer phone;

    @Basic
    @Column(name = "EMAIL")
    private String email;

    @Basic
    @Column(name = "product_id")
    private Long productId;

    @Basic
    @Lob
    @Column(name = "CONTENT")
    private String content;

    @Basic
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
}
