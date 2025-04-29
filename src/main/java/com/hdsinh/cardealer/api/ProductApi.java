package com.hdsinh.cardealer.api;

import com.hdsinh.cardealer.dto.ObjectDto;
import com.hdsinh.cardealer.dto.ProductDto;
import com.hdsinh.cardealer.entities.Product;
import com.hdsinh.cardealer.repository.Manufacturer.ManufacturerRepository;
import com.hdsinh.cardealer.service.Product.ProductService;
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
import java.util.*;

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
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "manufacturer", required = false) Long manufacturerId,
            @RequestParam(name = "fuel", required = false) String fuel,
            @RequestParam(name = "minOdo", required = false) Integer minOdo,
            @RequestParam(name = "maxOdo", required = false) Integer maxOdo,
            @RequestParam(name = "gearbox", required = false) String gearbox,
            @RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(name = "start", required = false) Integer start,
            @RequestParam(name = "total", required = false) Integer total) {

        ObjectDto objectDto = null;
        try {
            objectDto = productService.loadAll(search, status, type, manufacturerId, fuel, minPrice, maxPrice,
                    minOdo, maxOdo, gearbox, start, total);
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

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateProduct(@PathVariable("id") Long id, @RequestBody Product updatedProduct) {
        Optional<Product> productOptional = productService.findById(id);
        if (!productOptional.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "fail");
            response.put("message", "Product not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Product existingProduct = productOptional.get();

        if (updatedProduct.getCode() != null) {
            existingProduct.setCode(updatedProduct.getCode());
        }
        if (updatedProduct.getName() != null) {
            existingProduct.setName(updatedProduct.getName());
        }
        if (updatedProduct.getStatus() != null) {
            existingProduct.setStatus(updatedProduct.getStatus());
        }
        if (updatedProduct.getQuantity() != null) {
            existingProduct.setQuantity(updatedProduct.getQuantity());
        }
        if (updatedProduct.getDescription() != null) {
            existingProduct.setDescription(updatedProduct.getDescription());
        }
        if (updatedProduct.getManufacturerId() != null) {
            existingProduct.setManufacturerId(updatedProduct.getManufacturerId());
        }
        if (updatedProduct.getGearbox() != null) {
            existingProduct.setGearbox(updatedProduct.getGearbox());
        }
        if (updatedProduct.getFuel() != null) {
            existingProduct.setFuel(updatedProduct.getFuel());
        }
        if (updatedProduct.getColor() != null) {
            existingProduct.setColor(updatedProduct.getColor());
        }
        if (updatedProduct.getPrice() != null) {
            existingProduct.setPrice(updatedProduct.getPrice());
        }
        if (updatedProduct.getType() != null) {
            existingProduct.setType(updatedProduct.getType());
        }
        if (updatedProduct.getOdo() != null) {
            existingProduct.setOdo(updatedProduct.getOdo());
        }
        if (updatedProduct.getNumSeat() != null) {
            existingProduct.setNumSeat(updatedProduct.getNumSeat());
        }
        if (updatedProduct.getFirstRegis() != null) {
            existingProduct.setFirstRegis(updatedProduct.getFirstRegis());
        }

        // Lưu lại sản phẩm đã cập nhật
        productService.updateProduct(existingProduct);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("product", existingProduct);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/outofstock")
    public List<Product> getOutOfStockProducts() {
        return productService.getOutOfStockProducts();
    }

}
