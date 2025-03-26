package com.hdsinh.cardealer.controllers;

import com.hdsinh.cardealer.entities.Product;
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

    @GetMapping
    public String adminPage(Model model) {
        model.addAttribute("activeMenu", "admin");
        return "admin/trangAdmin";
    }

    @GetMapping("/nhan-vien")
    public String quanlyNhanVien(Model model) {
        model.addAttribute("activeMenu", "nhan-vien");
        return "admin/quanlyNhanvien";
    }

    @GetMapping("/san-pham")
    public String quanlySanPham(Model model) {
        List<Product> products = productService.getAllProducts();
        if (!products.isEmpty()) {
            System.out.println("ManufacturerName của sản phẩm đầu tiên: " + products.get(0).getManufacturerName());
        }
        model.addAttribute("products", products);
        return "admin/quanlySanpham"; // Tên view (admin/products.html)
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
