package com.hdsinh.cardealer.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Entity
@Table(name = "EMPLOYEE", schema = "SMH", catalog = "")
@Slf4j
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "NAME")
    private String name;

    @Basic
    @Column(name = "SEX")
    private String sex;

    @Basic
    @Column(name = "PHONE")
    private Integer phone;

    @Basic
    @Column(name = "EMAIL")
    private String email;

    @Basic
    @Column(name = "POSITION")
    private String position;

    @Basic
    @Column(name = "ADDRESS")
    private String address;

    @Basic
    @Column(name = "USER_ID")
    private Long userId;


}
