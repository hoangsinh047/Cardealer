package com.hdsinh.cardealer.api;

import com.hdsinh.cardealer.dto.BillDto;
import com.hdsinh.cardealer.dto.ObjectDto;
import com.hdsinh.cardealer.entities.Bill;
import com.hdsinh.cardealer.entities.Employee;
import com.hdsinh.cardealer.entities.Product;
import com.hdsinh.cardealer.services.Bill.BillService;
import com.hdsinh.cardealer.services.Employee.EmployeeService;
import com.hdsinh.cardealer.services.Product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

@RestController
@RequestMapping("api/bill")
@Slf4j
public class BillApi {
    private final BillService billService;

    private final ProductService productService;

    private final EmployeeService employeeService;

    public BillApi(BillService billService, ProductService productService, EmployeeService employeeService) {
        this.billService = billService;
        this.productService = productService;
        this.employeeService = employeeService;
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
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "tenKhachHang", required = false) String name,
            @RequestParam(value = "soDienThoai", required = false) String phone,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "tenNhanVien", required = false) Long employeeId,
            @RequestParam(value = "createdDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdDate ,
            @RequestParam(value = "tenSanPham", required = false) Long productId,
            @RequestParam(value = "soLuong", required = false) Integer quantity,
            @RequestParam(value = "tongGia", required = false) BigDecimal price) {

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

    @GetMapping("/{id}")
    public ResponseEntity<BillDto> getBill(@PathVariable Long id) {
        Bill bill = billService.findById(id);
        if (bill == null) {
            return ResponseEntity.notFound().build();
        }

        if (bill.getProductId() != null) {
            Product product = productService.getProductById(bill.getProductId());
            if (product != null) {
                bill.setProductName(product.getName());
            }
        }

        if (bill.getEmployeeId() != null) {
            Employee employee = employeeService.getEmployeeById(bill.getEmployeeId());
            if (employee != null) {
                bill.setEmployeeName(employee.getName());
            }
        }

        BillDto billDto = new BillDto(bill);
        return ResponseEntity.ok(billDto);
    }

    @GetMapping("/total-revenue")
    public ResponseEntity<Double> getTotalRevenue() {
        Double totalRevenue = billService.getTotalRevenue();
        return ResponseEntity.ok(totalRevenue);
    }

    @GetMapping("/revenue-by-month")
    public ResponseEntity<Map<String, Long>> getRevenueByMonth() {
        List<Bill> bills = billService.findAll(); // hoặc repo.findAll()

        Map<String, Long> revenueByMonth = new HashMap<>();
        // Khởi tạo tất cả 12 tháng với 0
        IntStream.rangeClosed(1, 12).forEach(i -> revenueByMonth.put("Tháng " + i, 0L));

        for (Bill bill : bills) {
            if (bill.getCreatedDate() != null && bill.getPrice() != null) {
                LocalDate createdDate = bill.getCreatedDate();
                if (createdDate.getYear() == LocalDateTime.now().getYear()) {
                    String month = "Tháng " + createdDate.getMonthValue();
                    revenueByMonth.put(month,
                            revenueByMonth.get(month) + bill.getPrice().longValue());
                }
            }
        }

        return ResponseEntity.ok(revenueByMonth);
    }
    @GetMapping("/order-count-by-month")
    public ResponseEntity<Map<String, Integer>> getOrderCountByMonth() {
        List<Bill> bills = billService.findAll(); // hoặc billRepository.findAll()

        Map<String, Integer> countByMonth = new LinkedHashMap<>();
        IntStream.rangeClosed(1, 12).forEach(m -> countByMonth.put("Tháng " + m, 0));

        for (Bill bill : bills) {
            if (bill.getCreatedDate() != null && bill.getCreatedDate().getYear() == LocalDate.now().getYear()) {
                int month = bill.getCreatedDate().getMonthValue();
                String monthLabel = "Tháng " + month;
                countByMonth.put(monthLabel, countByMonth.get(monthLabel) + 1);
            }
        }

        return ResponseEntity.ok(countByMonth);
    }


}
