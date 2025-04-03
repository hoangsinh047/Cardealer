package com.hdsinh.cardealer.dto;

import com.hdsinh.cardealer.entities.Customer;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class CustomerDto {
    private Long id;
    private String code;
    private Integer phone;
    private String email;
    private Long productId;
    private String productName;
    private LocalDateTime createdDate;
    private String content;

    public CustomerDto() {

    }
    public CustomerDto(Long id, String code, Integer phone, String email, Long productId, String productName,
                       LocalDateTime createdDate, String content) {
        this.id = id;
        this.code = code;
        this.phone = phone;
        this.email = email;
        this.productId = productId;
        this.productName = productName;
        this.createdDate = createdDate;
        this.content = content;

    }
}
