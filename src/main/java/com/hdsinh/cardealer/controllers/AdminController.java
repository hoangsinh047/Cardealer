package com.hdsinh.cardealer.controllers;

import com.hdsinh.cardealer.dto.BillDto;
import com.hdsinh.cardealer.entities.*;
import com.hdsinh.cardealer.services.Bill.BillService;
import com.hdsinh.cardealer.services.Customer.CustomerService;
import com.hdsinh.cardealer.services.Employee.EmployeeService;
import com.hdsinh.cardealer.services.Product.ProductService;
import com.hdsinh.cardealer.services.Testdrive.TestdriveService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@SessionAttributes("admin")
public class AdminController {

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

    @GetMapping
    public String adminPage(Model model) {
        model.addAttribute("activeMenu", "admin");
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

    @GetMapping("/san-pham")
    public String quanlySanPham(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
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

    @GetMapping("/bao-cao")
    public String quanlyBaoCao(Model model) {
        List<Bill> bills = billService.findAll();
        List<Employee> employees = employeeService.findAll();
        List<Product> product = productService.getAllProducts();
        model.addAttribute("bill", bills);
        model.addAttribute("employees", employees);
        model.addAttribute("product", product);
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
