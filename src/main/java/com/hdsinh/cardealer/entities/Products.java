package com.hdsinh.cardealer.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;


@Entity
@Table(name = "products")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    private String status;
    private String description;
    private String manufacturer_id;
    private String gearbox;
    private String fuel;
    private String color;
    private String image_url;
    private BigDecimal price;
    private Integer quantity;

    private String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    private String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }


    private String getImage_url() {
        return image_url;
    }

    private void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    private BigDecimal getPrice() {
        return price;
    }

    private void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
