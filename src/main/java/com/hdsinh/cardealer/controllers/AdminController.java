package com.hdsinh.cardealer.controllers;

import com.hdsinh.cardealer.dto.BillDto;
import com.hdsinh.cardealer.dto.ProductDto;
import com.hdsinh.cardealer.entities.*;
import com.hdsinh.cardealer.services.Bill.BillService;
import com.hdsinh.cardealer.services.Customer.CustomerService;
import com.hdsinh.cardealer.services.Employee.EmployeeService;
import com.hdsinh.cardealer.services.Manufacturer.ManufacturerService;
import com.hdsinh.cardealer.services.Product.ProductService;
import com.hdsinh.cardealer.services.Testdrive.TestdriveService;
import com.itextpdf.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin")
@SessionAttributes("admin")
public class AdminController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProductService productService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private BillService billService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TestdriveService testdriveService;

    @Autowired
    private ManufacturerService manufacturerService;

    @GetMapping
    public String adminPage(Model model) {
        model.addAttribute("activeMenu", "admin");
        List<Customer> customer = customerService.findAll();
        model.addAttribute("customer", customer);
        return "admin/trangAdmin";
    }

    @GetMapping("/nhan-vien")
    public String quanlyNhanVien(Model model) {
        List<Employee> employee = employeeService.findAll();
        model.addAttribute("employee", employee);
        model.addAttribute("activeMenu", "nhan-vien");
        return "admin/quanlyNhanvien";
    }

    @GetMapping("/nhan-vien/new")
    public String themNhanvien(Model model) {
        return "admin/addNhanvien";
    }

    @GetMapping("/hang-xe")
    public String quanlyHangXe(Model model) {
        List<Manufacturer> manufacturers = manufacturerService.getAllManufacturers();
        model.addAttribute("manufacturer", manufacturers);
        return "admin/quanlyHangxe";
    }

    @GetMapping("/hang-xe/new")
    public String themHangxe(Model model) {
        return "admin/addHangxe";
    }

    @GetMapping("/san-pham")
    public String quanlySanPham(Model model) {
        List<Product> product = productService.getAllProducts();
        model.addAttribute("products", product);
        return "admin/quanlySanpham";
    }

    @GetMapping("/san-pham/new")
    public String themSanpham(Model model) {
        return "admin/addSanpham";
    }

    @GetMapping("/don-hang")
    public String quanlyDonhang(Model model) {
        List<Bill> bills = billService.findAll();
        List<Employee> employees = employeeService.findAll();
        List<Product> product = productService.getAllProducts();
        model.addAttribute("bill", bills);
        model.addAttribute("employees", employees);
        model.addAttribute("product", product);
        return "admin/quanlyDonhang";
    }

    @GetMapping("/don-hang/new")
    public String taomoiHoadon(Model model) {
        List<Employee> employees = employeeService.findAll();
        List<Product> product = productService.getAllProducts();
        model.addAttribute("employees", employees);
        model.addAttribute("product", product);
        return "admin/addHoadon";
    }

    @GetMapping("/don-hang/{id}")
    public String detailsHoadon(Model model, @PathVariable Long id) {
        String apiUrl = "http://localhost:8080/api/bill/" + id;
        BillDto bill = restTemplate.getForObject(apiUrl, BillDto.class);
        List<Employee> employees = employeeService.findAll();
        List<Product> product = productService.getAllProducts();
        model.addAttribute("bill", bill);  // Chỉ gửi đối tượng Bill
        model.addAttribute("employees", employees);
        model.addAttribute("product", product);
        return "admin/bill-details";
    }

    @GetMapping("/don-hang/export/{id}")
    public void exportInvoice(@PathVariable Long id, HttpServletResponse response) {
        try {
            // Generate the PDF as a byte array
            byte[] pdfBytes = billService.generateInvoicePdf(id);

            // Set response headers for downloading
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=hoa_don_ban_hang_" + id + ".pdf");

            // Write the PDF byte array to the response output stream
            response.getOutputStream().write(pdfBytes);
        } catch (DocumentException | IOException e) {
            // Handle the exception and return an error message or page
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating PDF: " + e.getMessage());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @GetMapping("/bao-cao")
    public String quanlyBaoCao(Model model) {
        List<Bill> bills = billService.findAll();
        List<Employee> employees = employeeService.findAll();
        List<Product> product = productService.getAllProducts();
        List<ProductDto> products = productService.getBestSellingProduct();
        List<Product> productStock = productService.getOutOfStockProducts();
        model.addAttribute("productStock", productStock);
        model.addAttribute("bill", bills);
        model.addAttribute("employees", employees);
        model.addAttribute("product", product);
        model.addAttribute("productsBest", products);
        return "admin/quanlyBaocao";
    }

    @GetMapping("/khach-hang")
    public String quanlyKhachhang(Model model) {
        List<Customer> customer = customerService.findAll();
        model.addAttribute("customer", customer);
        return "admin/quanlyKhachhang";
    }

    @GetMapping("/lai-thu")
    public String quanlyLaithu(Model model) {
        List<Testdrive> testdrive = testdriveService.findAll();
        model.addAttribute("testdrive", testdrive);
        return "admin/danhsachDangkylaithu";
    }

}
