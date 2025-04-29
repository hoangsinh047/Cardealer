package com.hdsinh.cardealer.service.Product;

import com.hdsinh.cardealer.dto.ObjectDto;
import com.hdsinh.cardealer.dto.ProductDto;
import com.hdsinh.cardealer.entities.Product;
import com.hdsinh.cardealer.repository.Bill.BillRepository;
import com.hdsinh.cardealer.repository.Manufacturer.ManufacturerRepository;
import com.hdsinh.cardealer.repository.Product.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final BillRepository billRepository;
    private final ProductRepository productRepository;
    private final ManufacturerRepository manufacturerRepository;

    public ProductServiceImpl(BillRepository billRepository, ProductRepository productRepository, ManufacturerRepository manufacturerRepository) {
        this.billRepository = billRepository;
        this.productRepository = productRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Long getLowStockCount() {
        return productRepository.countLowStockProducts();
    }

    public List<Product> findAll() {
        List<Product> products = productRepository.findAll();

        for (Product product : products) {
            // Lấy tên sản phẩm từ productId
            manufacturerRepository.findById(product.getManufacturerId()).ifPresent(manufacturer ->
                    product.setManufacturerName(manufacturer.getName())
            );
        }

        return products;
    }

    @Override
    public ObjectDto loadAll(String search, String status, String type, Long manufacturerId, String fuel,
                             BigDecimal minPrice, BigDecimal maxPrice, Integer minOdo, Integer maxOdo, String gearbox,
                             Integer start, Integer total) {
        ObjectDto res = new ObjectDto();

        // Lấy danh sách từ repository
        List<Product> list = productRepository.loadAll(search, status, manufacturerId, type, minPrice, maxPrice,
                minOdo, maxOdo, fuel, gearbox, start, total);

        List<Product> products = productRepository.findAll();

        for (Product product : products) {
            if (product.getManufacturerId() != null) {
                manufacturerRepository.findById(product.getManufacturerId()).ifPresent(manufacturer ->
                        product.setManufacturerName(manufacturer.getName())
                );
            }
        }

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

    public void updateProduct(Product product) {
        productRepository.save(product);
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

    public List<ProductDto> getBestSellingProduct() {
        List<Object[]> results = billRepository.findTotalQuantitySoldForProducts();
        List<ProductDto> bestSellingProducts = new ArrayList<>();

        for (Object[] result : results) {
            Long productId = (Long) result[0];
            Long totalQuantity = (Long) result[1];

            Optional<Product> productOpt = productRepository.findById(productId);
            productOpt.ifPresent(product -> {
                ProductDto dto = new ProductDto(product);
                dto.setTotalSoldQuantity(totalQuantity); // cần thêm field này trong DTO
                bestSellingProducts.add(dto);


            });
        }

        return bestSellingProducts;
    }

    public List<Product> getOutOfStockProducts() {
        return productRepository.findByQuantityEquals(0);
    }
}