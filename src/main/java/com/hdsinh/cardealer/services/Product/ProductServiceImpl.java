package com.hdsinh.cardealer.services.Product;

import com.hdsinh.cardealer.dto.ObjectDto;
import com.hdsinh.cardealer.entities.Product;
import com.hdsinh.cardealer.repository.product.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public ObjectDto loadAll(String search, Integer start, Integer total) {
        ObjectDto res = new ObjectDto();

        // Lấy danh sách từ repository
        List<Product> list = productRepository.loadAll(search, start, total);

        // Lấy tổng số bản ghi thỏa điều kiện
        Long count = productRepository.countAll(search);

        // Cập nhật dữ liệu
        res.setData(list);
        res.setCount(count);

        return res;
    }

    @Override
    public Product addProduct(Product product, MultipartFile imageUrl) throws IOException {

        if (imageUrl != null && !imageUrl.isEmpty()) {
            String uploadDir = "D:/Cardealer/src/main/resources/static/assets/images/img_test/";
            File uploadPath = new File(uploadDir);
            if (!uploadPath.exists()) {
                uploadPath.mkdirs(); // Tạo thư mục nếu chưa có
            }

            // Đổi tên file ảnh để tránh trùng lặp
            String fileName = UUID.randomUUID().toString() + "_" + imageUrl.getOriginalFilename();
            String filePath = uploadDir + fileName;
            File file = new File(filePath);

            // Lưu file ảnh vào thư mục server
            imageUrl.transferTo(file);

            // Gán đường dẫn vào product
            product.setImageUrl("/assets/images/img_test/" + fileName);
        }
        return productRepository.save(product);
    }
}