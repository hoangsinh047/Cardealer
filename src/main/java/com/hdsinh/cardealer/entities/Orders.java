package com.hdsinh.cardealer.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "address")
    private String address;

    @Column(name = "note")
    private String note;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "orderdate")
    private Date orderdate;
    @Column(name = "datereceived")
    private Date datereceived;

    @Column(name = "numberphone")
    private Integer numberphone;

    @Column(name = "user_id")
    private Integer user_id;

    @Column(name = "price")
    private BigDecimal price;

}
