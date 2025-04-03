package com.hdsinh.cardealer.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class BillDto {
    private Long id;
    private String code;
    private Long productId;
    private String productName;
    private Long employeeId;
    private String employeeName;
    private Long customerId;
    private String customerName;
    private Integer quantity;
    private Date createdDate;
    private BigDecimal price;

    public BillDto() {

    }

    public BillDto(Long productId,String code, String productName, Long employeeId, String employeeName, Long customerId,
                   String customerName, Integer quantity, Date createdDate, BigDecimal price) {
        this.productId = productId;
        this.code = code;
        this.productName = productName;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.customerId = customerId;
        this.customerName = customerName;
        this.quantity = quantity;
        this.createdDate = createdDate;
        this.price = price;
    }
}
