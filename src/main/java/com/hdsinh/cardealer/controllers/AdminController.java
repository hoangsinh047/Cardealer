//package com.hdsinh.cardealer.controllers;
//
//import com.hdsinh.cardealer.dto.BillDto;
//import com.hdsinh.cardealer.dto.ProductDto;
//import com.hdsinh.cardealer.entities.*;
//import com.hdsinh.cardealer.service.Bill.BillService;
//import com.hdsinh.cardealer.service.Customer.CustomerService;
//import com.hdsinh.cardealer.service.Employee.EmployeeService;
//import com.hdsinh.cardealer.service.Manufacturer.ManufacturerService;
//import com.hdsinh.cardealer.service.Product.ProductService;
//import com.hdsinh.cardealer.service.Testdrive.TestdriveService;
//import com.hdsinh.cardealer.service.User.UserService;
//import com.itextpdf.text.DocumentException;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Optional;
//
//@Slf4j
//@Controller
//@RequestMapping("/admin")
//@SessionAttributes("admin")
//public class AdminController {
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Autowired
//    private ProductService productService;
//
//    @Autowired
//    private EmployeeService employeeService;
//
//    @Autowired
//    private BillService billService;
//
//    @Autowired
//    private CustomerService customerService;
//
//    @Autowired
//    private TestdriveService testdriveService;
//
//    @Autowired
//    private ManufacturerService manufacturerService;
//
//    @Autowired
//    private UserService userService;
//
//    @GetMapping
//    public String adminPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
//        String username = userDetails.getUsername();
//
//        Optional<Users> optionalUser = userService.findByUsername(username);
//        if (optionalUser.isPresent()) {
//            Users user = optionalUser.get();
//
//            // Tìm tên nhân viên nếu có employeeId
//            if (user.getEmployeeId() != null) {
//                Optional<Employee> optionalEmployee = employeeService.findById(user.getEmployeeId());
//                optionalEmployee.ifPresent(employee -> user.setEmployeeName(employee.getName()));
//            }
//
//            model.addAttribute("loggedInUser", user);
//        } else {
//            // fallback nếu không tìm thấy user (tuỳ ý)
//            model.addAttribute("loggedInUser", null);
//        }
//
//
//        model.addAttribute("activeMenu", "admin");
//        List<Customer> customer = customerService.findAll();
//        model.addAttribute("customer", customer);
////        model.addAttribute("loggedInUser", username);
//        return "admin/trangAdmin";
//    }
//
//    @GetMapping("/nhan-vien")
//    public String quanlyNhanVien(Model model) {
//        List<Employee> employee = employeeService.findAll();
//        model.addAttribute("employee", employee);
//        model.addAttribute("activeMenu", "nhan-vien");
//        return "admin/quanlyNhanvien";
//    }
//
//    @GetMapping("/nhan-vien/new")
//    public String themNhanvien(Model model) {
//        return "admin/addNhanvien";
//    }
//
//    @GetMapping("/hang-xe")
//    public String quanlyHangXe(Model model) {
//        List<Manufacturer> manufacturers = manufacturerService.getAllManufacturers();
//        model.addAttribute("manufacturer", manufacturers);
//        return "admin/quanlyHangxe";
//    }
//
//    @GetMapping("/hang-xe/new")
//    public String themHangxe(Model model) {
//        return "admin/addHangxe";
//    }
//
//    @GetMapping("/san-pham")
//    public String quanlySanPham(Model model) {
//        List<Product> product = productService.getAllProducts();
//        model.addAttribute("products", product);
//        return "admin/quanlySanpham";
//    }
//
//    @GetMapping("/san-pham/new")
//    public String themSanpham(Model model) {
//        return "admin/addSanpham";
//    }
//
//    @GetMapping("/don-hang")
//    public String quanlyDonhang(Model model) {
//        List<Bill> bills = billService.findAll();
//        List<Employee> employees = employeeService.findAll();
//        List<Product> product = productService.getAllProducts();
//        model.addAttribute("bill", bills);
//        model.addAttribute("employees", employees);
//        model.addAttribute("product", product);
//        return "admin/quanlyDonhang";
//    }
//
//    @GetMapping("/don-hang/new")
//    public String taomoiHoadon(Model model) {
//        List<Employee> employees = employeeService.findAll();
//        List<Product> product = productService.getAllProducts();
//        model.addAttribute("employees", employees);
//        model.addAttribute("product", product);
//        return "admin/addHoadon";
//    }
//
//    @GetMapping("/don-hang/{id}")
//    public String detailsHoadon(Model model, @PathVariable Long id) {
//        String apiUrl = "http://localhost:8080/api/bill/" + id;
//        BillDto bill = restTemplate.getForObject(apiUrl, BillDto.class);
//        List<Employee> employees = employeeService.findAll();
//        List<Product> product = productService.getAllProducts();
//        model.addAttribute("bill", bill);  // Chỉ gửi đối tượng Bill
//        model.addAttribute("employees", employees);
//        model.addAttribute("product", product);
//        return "admin/bill-details";
//    }
//
//    @GetMapping("/don-hang/export/{id}")
//    public void exportInvoice(@PathVariable Long id, HttpServletResponse response) {
//        try {
//            // Generate the PDF as a byte array
//            byte[] pdfBytes = billService.generateInvoicePdf(id);
//
//            // Set response headers for downloading
//            response.setContentType("application/pdf");
//            response.setHeader("Content-Disposition", "attachment; filename=hoa_don_ban_hang_" + id + ".pdf");
//
//            // Write the PDF byte array to the response output stream
//            response.getOutputStream().write(pdfBytes);
//        } catch (DocumentException | IOException e) {
//            // Handle the exception and return an error message or page
//            try {
//                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating PDF: " + e.getMessage());
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
//
//    @GetMapping("/bao-cao")
//    public String quanlyBaoCao(Model model) {
//        List<Bill> bills = billService.findAll();
//        List<Employee> employees = employeeService.findAll();
//        List<Product> product = productService.getAllProducts();
//        List<ProductDto> products = productService.getBestSellingProduct();
//        List<Product> productStock = productService.getOutOfStockProducts();
//        model.addAttribute("productStock", productStock);
//        model.addAttribute("bill", bills);
//        model.addAttribute("employees", employees);
//        model.addAttribute("product", product);
//        model.addAttribute("productsBest", products);
//        return "admin/quanlyBaocao";
//    }
//
//    @GetMapping("/khach-hang")
//    public String quanlyKhachhang(Model model) {
//        List<Customer> customer = customerService.findAll();
//        model.addAttribute("customer", customer);
//        return "admin/quanlyKhachhang";
//    }
//
//    @GetMapping("/lai-thu")
//    public String quanlyLaithu(Model model) {
//        List<Testdrive> testdrive = testdriveService.findAll();
//        model.addAttribute("testdrive", testdrive);
//        return "admin/danhsachDangkylaithu";
//    }
//
//    @GetMapping("/tai-khoan")
//    public String quanlyTaikhoan(Model model) {
//        List<Users> user = userService.findAll();
//        model.addAttribute("user", user);
//        return "admin/quanlyTaiKhoan";
//    }
//
//    @GetMapping("/tai-khoan/new")
//    public String taomoiTaikhoan(Model model) {
//        List<Employee> employees = employeeService.findAll();
//        model.addAttribute("employees", employees);
//        return "admin/addTaikhoan";
//    }
//}
package com.hdsinh.cardealer.controllers;

import com.hdsinh.cardealer.dto.BillDto;
import com.hdsinh.cardealer.dto.ProductDto;
import com.hdsinh.cardealer.entities.*;
import com.hdsinh.cardealer.service.Bill.BillService;
import com.hdsinh.cardealer.service.Customer.CustomerService;
import com.hdsinh.cardealer.service.Employee.EmployeeService;
import com.hdsinh.cardealer.service.Manufacturer.ManufacturerService;
import com.hdsinh.cardealer.service.Product.ProductService;
import com.hdsinh.cardealer.service.Testdrive.TestdriveService;
import com.hdsinh.cardealer.service.User.UserService;
import com.itextpdf.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private UserService userService;

    /**
     * Đặt loggedInUser và activeMenu vào model
     */
    private void populateUserAndMenu(Model model, Authentication authentication, String menu) {
        Users user = null;
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Optional<Users> optionalUser = userService.findByUsername(username);
            if (optionalUser.isPresent()) {
                user = optionalUser.get();
                if (user.getEmployeeId() != null) {
                    Optional<Employee> empOpt = employeeService.findById(user.getEmployeeId());
                    if (empOpt.isPresent()) {
                        user.setEmployeeName(empOpt.get().getName());
                    }
                }
            }
        }
        model.addAttribute("loggedInUser", user);
        model.addAttribute("activeMenu", menu);
    }

    @GetMapping
    public String adminPage(Authentication authentication, Model model) {
        populateUserAndMenu(model, authentication, "dashboard");
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        return "admin/trangAdmin";
    }

    @GetMapping("/nhan-vien")
    public String quanlyNhanVien(Authentication authentication, Model model) {
        populateUserAndMenu(model, authentication, "nhan-vien");
        model.addAttribute("employee", employeeService.findAll());
        return "admin/quanlyNhanvien";
    }

    @GetMapping("/nhan-vien/new")
    public String themNhanvien(Authentication authentication, Model model) {
        populateUserAndMenu(model, authentication, "nhan-vien");
        return "admin/addNhanvien";
    }

    @GetMapping("/hang-xe")
    public String quanlyHangXe(Authentication authentication, Model model) {
        populateUserAndMenu(model, authentication, "hang-xe");
        model.addAttribute("manufacturer", manufacturerService.getAllManufacturers());
        return "admin/quanlyHangxe";
    }

    @GetMapping("/hang-xe/new")
    public String themHangxe(Authentication authentication, Model model) {
        populateUserAndMenu(model, authentication, "hang-xe");
        return "admin/addHangxe";
    }

    @GetMapping("/san-pham")
    public String quanlySanPham(Authentication authentication, Model model) {
        populateUserAndMenu(model, authentication, "san-pham");
        model.addAttribute("products", productService.getAllProducts());
        return "admin/quanlySanpham";
    }

    @GetMapping("/san-pham/new")
    public String themSanpham(Authentication authentication, Model model) {
        populateUserAndMenu(model, authentication, "san-pham");
        return "admin/addSanpham";
    }

    @GetMapping("/don-hang")
    public String quanlyDonhang(Authentication authentication, Model model) {
        populateUserAndMenu(model, authentication, "don-hang");
        model.addAttribute("bill", billService.findAll());
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("product", productService.getAllProducts());
        return "admin/quanlyDonhang";
    }

    @GetMapping("/don-hang/new")
    public String taomoiHoadon(Authentication authentication, Model model) {
        populateUserAndMenu(model, authentication, "don-hang");
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("product", productService.getAllProducts());
        return "admin/addHoadon";
    }

    @GetMapping("/don-hang/{id}")
    public String detailsHoadon(Authentication authentication, Model model, @PathVariable Long id) {
        populateUserAndMenu(model, authentication, "don-hang");
        BillDto bill = restTemplate.getForObject("http://localhost:8080/api/bill/" + id, BillDto.class);
        model.addAttribute("bill", bill);
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("product", productService.getAllProducts());
        return "admin/bill-details";
    }

    @GetMapping("/don-hang/export/{id}")
    public void exportInvoice(@PathVariable Long id, HttpServletResponse response) {
        try {
            byte[] pdfBytes = billService.generateInvoicePdf(id);
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=hoa_don_ban_hang_" + id + ".pdf");
            response.getOutputStream().write(pdfBytes);
        } catch (DocumentException | IOException e) {
            log.error("Error generating PDF", e);
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating PDF: " + e.getMessage());
            } catch (IOException ex) {
                log.error("Error sending error response", ex);
            }
        }
    }

    @GetMapping("/bao-cao")
    public String quanlyBaoCao(Authentication authentication, Model model) {
        populateUserAndMenu(model, authentication, "bao-cao");
        model.addAttribute("bill", billService.findAll());
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("product", productService.getAllProducts());
        model.addAttribute("productsBest", productService.getBestSellingProduct());
        model.addAttribute("productStock", productService.getOutOfStockProducts());
        return "admin/quanlyBaocao";
    }

    @GetMapping("/khach-hang")
    public String quanlyKhachhang(Authentication authentication, Model model) {
        populateUserAndMenu(model, authentication, "khach-hang");
        model.addAttribute("customer", customerService.findAll());
        return "admin/quanlyKhachhang";
    }

    @GetMapping("/lai-thu")
    public String quanlyLaithu(Authentication authentication, Model model) {
        populateUserAndMenu(model, authentication, "lai-thu");
        model.addAttribute("testdrive", testdriveService.findAll());
        return "admin/danhsachDangkylaithu";
    }

    @GetMapping("/tai-khoan")
    public String quanlyTaikhoan(Authentication authentication, Model model) {
        populateUserAndMenu(model, authentication, "tai-khoan");
        model.addAttribute("user", userService.findAll());
        return "admin/quanlyTaiKhoan";
    }

    @GetMapping("/tai-khoan/new")
    public String taomoiTaikhoan(Authentication authentication, Model model) {
        populateUserAndMenu(model, authentication, "tai-khoan");
        model.addAttribute("employees", employeeService.findAll());
        return "admin/addTaikhoan";
    }
}

