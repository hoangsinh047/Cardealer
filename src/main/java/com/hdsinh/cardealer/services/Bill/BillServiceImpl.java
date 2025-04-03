package com.hdsinh.cardealer.services.Bill;

import com.hdsinh.cardealer.dto.ObjectDto;
import com.hdsinh.cardealer.dto.BillDto;
import com.hdsinh.cardealer.entities.Bill;
import com.hdsinh.cardealer.entities.Employee;
import com.hdsinh.cardealer.entities.Product;
import com.hdsinh.cardealer.repository.Bill.BillRepository;
import com.hdsinh.cardealer.repository.Employee.EmployeeRepository;
import com.hdsinh.cardealer.repository.Product.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;


    private final EmployeeRepository employeeRepository;


    private final ProductRepository productRepository;

    public BillServiceImpl(BillRepository billRepository, EmployeeRepository employeeRepository, ProductRepository productRepository) {
        this.billRepository = billRepository;
        this.employeeRepository = employeeRepository;
        this.productRepository = productRepository;
    }

    public List<Bill> findAll() {
        List<Bill> bills = billRepository.findAll();

        for (Bill bill : bills) {
            // Lấy tên sản phẩm từ productId
            productRepository.findById(bill.getProductId()).ifPresent(product ->
                    bill.setProductName(product.getName())
            );

            // Lấy tên nhân viên từ employeeId
            employeeRepository.findById(bill.getEmployeeId()).ifPresent(employee ->
                    bill.setEmployeeName(employee.getName())
            );
        }

        return bills;
    }

    @Override
    public ObjectDto loadAll(String search, Integer start, Integer total) {
        ObjectDto res = new ObjectDto();

        // Lấy danh sách từ repository
        List<BillDto> list = billRepository.loadAll(search, start, total);

        // Lấy tổng số bản ghi thỏa điều kiện
        Long count = billRepository.countAll(search);

        // Cập nhật dữ liệu
        res.setData(list);
        res.setCount(count);

        return res;
    }

    @Transactional
    public Bill save(Bill bill) {
        // Tìm sản phẩm theo productId
        Product product = productRepository.findById(bill.getProductId())
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        // Kiểm tra số lượng có đủ không
        if (product.getQuantity() < bill.getQuantity()) {
            throw new RuntimeException("Số lượng sản phẩm không đủ");
        }

        // Trừ số lượng sản phẩm trong kho
        product.setQuantity(product.getQuantity() - bill.getQuantity());
        productRepository.save(product);

        // Lưu hóa đơn
        return billRepository.save(bill);
    }


    public Double getTotalRevenue() {
        return billRepository.getTotalRevenue();
    }
}
