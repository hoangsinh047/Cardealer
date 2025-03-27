package com.hdsinh.cardealer.controllers;

import com.hdsinh.cardealer.entities.Employee;
import com.hdsinh.cardealer.entities.Product;
import com.hdsinh.cardealer.services.Employee.EmployeeService;
import com.hdsinh.cardealer.services.Product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin")
@SessionAttributes("admin")
public class AdminController {

    @Autowired
    private ProductService productService;

    @Autowired
    private EmployeeService employeeService;

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

    @GetMapping("don-hang")
    public String quanlyDonhang() {
        return "admin/quanlyDonhang";
    }

    @GetMapping("bao-cao")
    public String quanlyBaoCao() {
        return "admin/quanlyBaocao";
    }


}
