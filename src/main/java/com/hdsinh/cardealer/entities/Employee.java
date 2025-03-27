package com.hdsinh.cardealer.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Date;

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
    @Column(name = "BIRTH", nullable = true)
    private Date birth;

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
    @Column(name = "DEGREE")
    private String degree;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;
}
