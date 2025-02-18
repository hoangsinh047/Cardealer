package com.hdsinh.cardealer.services;

import com.hdsinh.cardealer.entities.CartItem;
import com.hdsinh.cardealer.model.Cart;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    public Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if(cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    public void addToCart(HttpSession session, Long productId, String productName, int quantity, double price) {
        Cart cart = getCart(session);
        CartItem item = new CartItem(productId, productName, price, quantity);
        cart.addItem(item);
    }

    public double getTotal(HttpSession session) {
        Cart cart = getCart(session);
        return cart.getTotal();
    }
}
