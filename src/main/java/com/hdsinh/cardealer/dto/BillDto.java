package com.hdsinh.cardealer.dto;

import com.hdsinh.cardealer.entities.Bill;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Data
public class BillDto {
    private Long id;
    private String code;
    private String name;
    private String phone;
    private String address;
    private Long productId;
    private String productName;
    private Long employeeId;
    private String employeeName;
    private Integer quantity;
    private LocalDate createdDate;
    private BigDecimal price;

    public BillDto() {

    }

    public BillDto(Bill bill) {
        this.id = bill.getId();
        this.name = bill.getName();
        this.code = bill.getCode();
        this.price = bill.getPrice();
        this.phone = bill.getPhone();
        this.quantity = bill.getQuantity();
        this.address = bill.getAddress();
        this.productId = bill.getProductId();
        this.employeeId = bill.getEmployeeId();
        this.productName = bill.getProductName();
        this.employeeName = bill.getEmployeeName();
        this.createdDate = bill.getCreatedDate();
    }
}
