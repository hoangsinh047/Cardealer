package com.hdsinh.cardealer.controllers;

import com.hdsinh.cardealer.entities.CartItem;
import org.springframework.ui.Model;
import com.hdsinh.cardealer.services.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

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
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        boolean productExitsts = false;
        for (CartItem item : cart) {
            if (item.getProductId().equals(productId)) {
                item.setQuantity(item.getQuantity() + quantity);
                productExitsts = true;
                break;
            }
        }

        if (!productExitsts) {
            cart.add(new CartItem(productId, productName, price, quantity));
        }

        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            model.addAttribute("cartItems", cart);
        }
        return "client/cart";
    }
}
