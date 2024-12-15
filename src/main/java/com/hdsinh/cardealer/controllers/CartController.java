package com.hdsinh.cardealer.controllers;

import com.hdsinh.cardealer.model.Cart;
import org.springframework.ui.Model;
import com.hdsinh.cardealer.services.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/addtocart")
    public String addToCart(@RequestParam Long productId,
                            @RequestParam int quantity,
                            @RequestParam String productName,
                            @RequestParam Double price,
                            HttpSession session) {
        cartService.addToCart(session, productId, productName, quantity, price);
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        Cart cart = cartService.getCart(session);
        model.addAttribute("cart", cart);
        model.addAttribute("total", cartService.getTotal(session));
        return "cart";
    }
}
