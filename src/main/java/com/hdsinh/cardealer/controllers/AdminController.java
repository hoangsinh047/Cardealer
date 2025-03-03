package com.hdsinh.cardealer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@SessionAttributes("admin")
public class AdminController {

    @GetMapping
    public String adminPage() {
        return "admin/trangAdmin";
    }

    @GetMapping("/nhan-vien")
    public String quanlyNhanVien() {
        return "admin/quanlyNhanvien";
    }

    @GetMapping("/ql-san-pham")
    public String quanlySanpham() {
        return "admin/quanlySanpham";
    }
}
