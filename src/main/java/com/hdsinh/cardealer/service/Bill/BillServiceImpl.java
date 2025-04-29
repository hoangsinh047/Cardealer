package com.hdsinh.cardealer.service.Bill;

import com.hdsinh.cardealer.dto.ObjectDto;
import com.hdsinh.cardealer.dto.BillDto;
import com.hdsinh.cardealer.entities.Bill;
import com.hdsinh.cardealer.entities.Employee;
import com.hdsinh.cardealer.entities.Product;
import com.hdsinh.cardealer.repository.Bill.BillRepository;
import com.hdsinh.cardealer.repository.Employee.EmployeeRepository;
import com.hdsinh.cardealer.repository.Product.ProductRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.math.BigDecimal;
import java.util.List;

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

    @Override
    public Bill findById(Long id) {
        return billRepository.findById(id).orElse(null);
    }

    public Double getTotalRevenue() {
        return billRepository.getTotalRevenue();
    }

    @Override
    public byte[] generateInvoicePdf(Long id) throws DocumentException, IOException {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        // Gán thông tin sản phẩm và nhân viên nếu chưa có
        Product product = productRepository.findById(bill.getProductId()).orElse(null);
        if (product != null) {
            bill.setProductName(product.getName());
        }

        Employee employee = employeeRepository.findById(bill.getEmployeeId()).orElse(null);
        if (employee != null) {
            bill.setEmployeeName(employee.getName());
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 36, 36, 36, 36);
        PdfWriter.getInstance(document, baos);
        document.open();

        // Font tiếng Việt
        BaseFont baseFont = BaseFont.createFont(
                getClass().getClassLoader().getResource("static/assets/fonts/times.ttf").getPath(),
                BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font titleFont = new Font(baseFont, 16, Font.BOLD);
        Font normalFont = new Font(baseFont, 12);
        Font tableHeaderFont = new Font(baseFont, 12, Font.BOLD);

        // Tiêu đề
        Paragraph title = new Paragraph("HÓA ĐƠN BÁN HÀNG", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);

        // Thông tin khách hàng
        document.add(new Paragraph("Mã hóa đơn: " + bill.getCode(), normalFont));
        document.add(new Paragraph("Khách hàng: " + (bill.getName() != null ? bill.getName() : "Chưa có tên"), normalFont));
        document.add(new Paragraph("SĐT: " + (bill.getPhone() != null ? bill.getPhone() : "Chưa có số điện thoại"), normalFont));
        document.add(new Paragraph("Địa chỉ: " + (bill.getAddress() != null ? bill.getAddress() : "Chưa có địa chỉ"), normalFont));
        document.add(new Paragraph("Nhân viên: " + (bill.getEmployeeName() != null ? bill.getEmployeeName() : "Chưa có tên nhân viên"), normalFont));
        document.add(new Paragraph("Ngày lập: " + bill.getCreatedDate(), normalFont));
        document.add(Chunk.NEWLINE);

        // Bảng sản phẩm
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1, 4, 2, 3, 3});

        String[] headers = {"STT", "Tên sản phẩm", "Số lượng", "Đơn giá (VNĐ)", "Thành tiền (VNĐ)"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, tableHeaderFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);
        }

        BigDecimal unitPrice = product.getPrice();  // Giá trị đơn giá của sản phẩm

        // Tính tổng tiền sản phẩm
        BigDecimal itemTotal = bill.getPrice().multiply(BigDecimal.valueOf(bill.getQuantity()));

        table.addCell(new PdfPCell(new Phrase("1", normalFont)));
        table.addCell(new PdfPCell(new Phrase(bill.getProductName(), normalFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(bill.getQuantity()), normalFont)));
        table.addCell(new PdfPCell(new Phrase(String.format("%,.0f", unitPrice), normalFont)));
        table.addCell(new PdfPCell(new Phrase(String.format("%,.0f", bill.getPrice()), normalFont)));

        document.add(table);
        document.add(Chunk.NEWLINE);

        // Tổng tiền
        Paragraph totalParagraph = new Paragraph("Tổng cộng: " + String.format("%,.0f", bill.getPrice()) + " VNĐ", tableHeaderFont);
        totalParagraph.setAlignment(Element.ALIGN_RIGHT);
        document.add(totalParagraph);

        document.close();
        return baos.toByteArray();
    }





}
