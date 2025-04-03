package com.hdsinh.cardealer.api;

import com.hdsinh.cardealer.dto.ObjectDto;
import com.hdsinh.cardealer.dto.ProductDto;
import com.hdsinh.cardealer.entities.Manufacturer;
import com.hdsinh.cardealer.entities.Product;
import com.hdsinh.cardealer.repository.Manufacturer.ManufacturerRepository;
import com.hdsinh.cardealer.services.Product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/product")
@CrossOrigin(origins = "*")
@Slf4j
public class ProductApi {
    private final ProductService productService;

    public ProductApi(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @GetMapping("/getAllProduct")
    public ResponseEntity<ObjectDto> getList(
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "start", required = false) Integer start,
            @RequestParam(name = "total", required = false) Integer total) {

        ObjectDto objectDto = null;
        try {
            objectDto = productService.loadAll(search,  start, total);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(objectDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(objectDto, HttpStatus.OK);
    }

    @GetMapping("/lowStockCount")
    public ResponseEntity<Long> getLowStockCount() {
        Long count = productService.getLowStockCount();
        return ResponseEntity.ok(count);
    }


//    @GetMapping("/{id}")
//    public Product getProductById(@PathVariable Long id) {
//        return productService.getProductById(id);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        // Tạo DTO để tách danh sách ảnh
        ProductDto productDto = new ProductDto(product);
        return ResponseEntity.ok(productDto);
    }

    /*------------------- Delete a Product --------------------------------- */
//    @DeleteMapping(value = "/{id}")
//    public ResponseEntity<Response> deleteIrCommandDetectionConfig(@PathVariable("id") long id) {
//        Response res = productService.delete(id);
//        return new ResponseEntity<>(res, HttpStatus.OK);
//    }

        @PostMapping("/add")
        public ResponseEntity<Product> addProduct(
                @RequestParam("code") String code,
                @RequestParam("tenXe") String name,
                @RequestParam("tinhTrang") String status,
                @RequestParam(value = "soKm", required = false) String odo,
                @RequestParam(value = "dangKy", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate firstRegis,
                @RequestParam("soLuong") int quantity,
                @RequestParam("hangXe") Long manufacturerId,
                @RequestParam("hopSo") String gearbox,
                @RequestParam("nhienLieu") String fuel,
                @RequestParam("kieuXe") String type,
                @RequestParam("mauSac") String color,
                @RequestParam("giaBan") BigDecimal price,
                @RequestParam("mota") String description,
                @RequestParam("soGhe") Integer num_seat,
                @RequestParam("ImageUpload") MultipartFile[] imageUrl) throws IOException {

            // Tạo đối tượng sản phẩm
            Product product = new Product();
            product.setCode(code);
            product.setName(name);
            product.setStatus(status);
            product.setOdo(odo);
            product.setFirstRegis(firstRegis);
            product.setQuantity(quantity);
            product.setManufacturerId(manufacturerId);
            product.setGearbox(gearbox);
            product.setFuel(fuel);
            product.setType(type);
            product.setColor(color);
            product.setPrice(price);
            product.setNumSeat(num_seat);
            product.setDescription(description);

            // Lưu sản phẩm vào DB
            Product savedProduct = productService.addProduct(product, imageUrl);

            return ResponseEntity.ok(savedProduct);
        }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<Product>> getPopularProducts() {
        List<Product> products = productService.getProductsWithBills();
        return ResponseEntity.ok(products);
    }

}
