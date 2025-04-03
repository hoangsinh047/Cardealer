package com.hdsinh.cardealer.services.Product;

import com.hdsinh.cardealer.dto.ObjectDto;
import com.hdsinh.cardealer.entities.Employee;
import com.hdsinh.cardealer.entities.Product;
import com.hdsinh.cardealer.repository.Product.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.loadAll("", 0, 100);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Long getLowStockCount() {
        return productRepository.countLowStockProducts();
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
    public Product addProduct(Product product, MultipartFile[] imageFiles) throws IOException {
        if (imageFiles != null && imageFiles.length > 0) {
            String uploadDir = "D:/Cardealer/src/main/resources/static/assets/images/img_test/";
            File uploadPath = new File(uploadDir);
            if (!uploadPath.exists()) {
                uploadPath.mkdirs(); // Tạo thư mục nếu chưa có
            }

            StringBuilder imageUrls = new StringBuilder();

            for (MultipartFile image : imageFiles) {
                if (!image.isEmpty()) {
                    // Đổi tên file để tránh trùng lặp
                    String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
                    String filePath = uploadDir + fileName;
                    File file = new File(filePath);

                    // Lưu file vào thư mục server
                    image.transferTo(file);

                    // Thêm đường dẫn ảnh vào chuỗi
                    if (imageUrls.length() > 0) {
                        imageUrls.append(","); // Ngăn cách các đường dẫn bằng dấu phẩy
                    }
                    imageUrls.append("/assets/images/img_test/").append(fileName);
                }
            }

            // Gán danh sách ảnh dưới dạng chuỗi vào sản phẩm
            product.setImageUrl(imageUrls.toString());
        }

        // Lưu sản phẩm vào database
        return productRepository.save(product);
    }




    public boolean delete(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }


    public List<Product> getProductsWithBills() {
        List<Product> products = productRepository.findProductsWithBills();

        for (Product p : products) {
            if (p.getManufacturer() != null) {
                p.setManufacturerName(p.getManufacturer().getName());
            }
        }
        return products;
    }
}