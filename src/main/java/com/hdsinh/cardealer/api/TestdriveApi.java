package com.hdsinh.cardealer.api;

import com.hdsinh.cardealer.dto.ObjectDto;
import com.hdsinh.cardealer.entities.Testdrive;
import com.hdsinh.cardealer.service.Testdrive.TestdriveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/test-drive")
@Slf4j
public class TestdriveApi {
    private final TestdriveService testdriveService;

    @Autowired
    public TestdriveApi(TestdriveService testdriveService) {
        this.testdriveService = testdriveService;
    }

    @GetMapping("/getAllList")
    public ResponseEntity<ObjectDto> getList(
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "start", required = false) Integer start,
            @RequestParam(name = "total", required = false) Integer total) {

        ObjectDto objectDto = null;
        try {
            objectDto = testdriveService.loadAll(search, start, total);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(objectDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(objectDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        testdriveService.delete(id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<Testdrive> addCustomer(
            @RequestParam("code") String code,
            @RequestParam("tenKhachhang") String name,
            @RequestParam("address") String address,
            @RequestParam("phoneNumber") Integer phone,
            @RequestParam("testDriveDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date expectedDate,
            @RequestParam("hasLicense") String drivingLicense,
            @RequestParam("tenSanPham") Long productId) {

        // Tạo đối tượng sản phẩm
        Testdrive testdrive = new Testdrive();
        testdrive.setCode(code);
        testdrive.setName(name);
        testdrive.setAddress(address);
        testdrive.setPhone(phone);
        testdrive.setExpectedDate(expectedDate);
        testdrive.setDrivingLicense(drivingLicense);
        testdrive.setProductId(productId);

        // Lưu sản phẩm vào DB
        Testdrive savedTestdrive = testdriveService.save(testdrive);

        return ResponseEntity.ok(savedTestdrive);
    }
}
