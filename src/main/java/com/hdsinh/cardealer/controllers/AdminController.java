package com.hdsinh.cardealer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
@SessionAttributes("admin")
public class AdminController {

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

    @GetMapping("/ql-san-pham")
    public String quanlySanpham() {
        return "admin/quanlySanpham";
    }

    @GetMapping("/ql-san-pham/new")
    public String themSanpham(Model model) {
        return "admin/addSanpham";
    }

    @GetMapping("ql-don-hang")
    public String quanlyDonhang() {
        return "admin/quanlyDonhang";
    }

    @GetMapping("noi-bo")
    public String quanlyNoiBo() {
        return "admin/quanlyNoibo";
    }

    @GetMapping("bao-cao")
    public String quanlyBaoCao() {
        return "admin/quanlyBaocao";
    }

    @GetMapping("bang-ke-luong")
    public String quanlyBangLuong() {
        return "admin/quanlyBangluong";
    }

    @GetMapping("lich-cong-tac")
    public String quanlyLichCongTac() {
        return "admin/lichCongTac";
    }

}
