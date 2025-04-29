package com.hdsinh.cardealer.service.Product;

import com.hdsinh.cardealer.dto.ObjectDto;
import com.hdsinh.cardealer.dto.ProductDto;
import com.hdsinh.cardealer.entities.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();

    Product getProductById(Long id);

    Optional<Product> findById(Long id);

    void updateProduct(Product product);

    Long getLowStockCount();

    List<Product> findAll();

    ObjectDto loadAll(String search, String status, String type, Long manufacturerId, String fuel,
                      BigDecimal minPrice, BigDecimal maxPrice, Integer minOdo, Integer maxOdo, String gearbox,
                      Integer start, Integer total);

    Product addProduct(Product product, MultipartFile[] imageFiles) throws IOException;

    boolean delete(Long id);

    List<Product> getProductsWithBills();

    List<ProductDto> getBestSellingProduct();

    List<Product> getOutOfStockProducts();

}
