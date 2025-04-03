package com.hdsinh.cardealer.services.Product;

import com.hdsinh.cardealer.dto.ObjectDto;
import com.hdsinh.cardealer.entities.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    Product getProductById(Long id);

    Long getLowStockCount();

    ObjectDto loadAll(String search, Integer start, Integer total);

    Product addProduct(Product product, MultipartFile[] imageFiles) throws IOException;

    boolean delete(Long id);

    List<Product> getProductsWithBills();
}
