package com.hdsinh.cardealer.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cartitem")
public class CartItem {
    @Id
    @Column(name = "Id")
    private Long productId;
    @Column(name = "Name")
    private String productName;
    @Column(name = "price")
    private Double price;
    @Column(name = "quantity")
    private int quantity;

    public CartItem(Long productId, String productName, Double price, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public CartItem() {

    }

    public void setId(Long id) {
        this.productId = id;
    }

    public Long getId() {
        return productId;
    }

    public void setName(String name) {
        this.productName = name;
    }

    public String getName() {
        return productName;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
