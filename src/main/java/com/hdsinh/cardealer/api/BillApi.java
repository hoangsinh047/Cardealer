package com.hdsinh.cardealer.api;

import com.hdsinh.cardealer.dto.ObjectDto;
import com.hdsinh.cardealer.entities.Bill;
import com.hdsinh.cardealer.services.Bill.BillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("api/bill")
@Slf4j
public class BillApi {
    private final BillService billService;

    public BillApi(BillService billService) {
        this.billService = billService;
    }

    @GetMapping("/getAllBill")
    public ResponseEntity<ObjectDto> getList(
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "start", required = false) Integer start,
            @RequestParam(name = "total", required = false) Integer total) {

        ObjectDto objectDto = null;
        try {
            objectDto = billService.loadAll(search, start, total);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(objectDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(objectDto, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Bill> addSale(
            @RequestParam("code") String code,
            @RequestParam("tenKhachHang") String name,
            @RequestParam("soDienThoai") String phone,
            @RequestParam("address") String address,
            @RequestParam("tenNhanVien") Long employeeId,
            @RequestParam("createdDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdDate ,
            @RequestParam("tenSanPham") Long productId,
            @RequestParam("soLuong") Integer quantity,
            @RequestParam("tongGia") BigDecimal price) {

        // Tạo đối tượng sản phẩm
        Bill bill = new Bill();
        bill.setCode(code);
        bill.setName(name);
        bill.setAddress(address);
        bill.setPhone(phone);
        bill.setEmployeeId(employeeId);
        bill.setCreatedDate(createdDate);
        bill.setProductId(productId);
        bill.setQuantity(quantity);
        bill.setPrice(price);

        // Lưu sản phẩm vào DB
        Bill savedBill = billService.save(bill);

        return ResponseEntity.ok(savedBill);
    }

    @GetMapping("/total-revenue")
    public ResponseEntity<Double> getTotalRevenue() {
        Double totalRevenue = billService.getTotalRevenue();
        return ResponseEntity.ok(totalRevenue);
    }

}
