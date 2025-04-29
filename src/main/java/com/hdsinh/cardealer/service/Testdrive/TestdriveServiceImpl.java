package com.hdsinh.cardealer.service.Testdrive;

import com.hdsinh.cardealer.dto.ObjectDto;
import com.hdsinh.cardealer.entities.Product;
import com.hdsinh.cardealer.entities.Testdrive;
import com.hdsinh.cardealer.repository.Product.ProductRepository;
import com.hdsinh.cardealer.repository.Testdrive.TestdriveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class TestdriveServiceImpl implements TestdriveService {
    private final TestdriveRepository testdriveRepository;

    @Autowired
    private ProductRepository productRepository;

    public TestdriveServiceImpl(TestdriveRepository testdriveRepository) {
        this.testdriveRepository = testdriveRepository;
    }

    public List<Testdrive> findAll() {
        List<Testdrive> testdrives = testdriveRepository.findAll();

        for (Testdrive testdrive : testdrives) {
            if (testdrive.getProductId() != null) {
                Product product = productRepository.findById(testdrive.getProductId()).orElse(null);
                if (product != null) {
                    testdrive.setProductName(product.getName()); // Giả sử Product có field name
                }
            }
        }
        return this.testdriveRepository.findAll();
    }


    public String generateEmployeeCode() {
        Random random = new Random();
        int number = random.nextInt(10000); // Tạo số ngẫu nhiên từ 0 đến 9999
        return String.format("EM%04d", number); // Định dạng thành EMxxxx
    }

    @Override
    public ObjectDto loadAll(String search, Integer start, Integer total) {
        ObjectDto res = new ObjectDto();

        // Lấy danh sách từ repository
        List<Testdrive> list = testdriveRepository.loadAll(search, start, total);

        // Lấy tổng số bản ghi thỏa điều kiện
        Long count = testdriveRepository.countAll(search);

        // Cập nhật dữ liệu
        res.setData(list);
        res.setCount(count);

        return res;
    }

    public boolean delete(Long id) {
        Optional<Testdrive> testdrive = testdriveRepository.findById(id);
        if (testdrive.isPresent()) {
            testdriveRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Testdrive save(Testdrive testDrive) {
        if (testDrive.getCode() == null || testDrive.getCode().isEmpty()) {
            testDrive.setCode(generateEmployeeCode());
        }
        return testdriveRepository.save(testDrive);
    }
}
