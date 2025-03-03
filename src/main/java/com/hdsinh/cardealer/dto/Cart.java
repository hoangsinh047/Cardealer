package com.hdsinh.cardealer.dto;

import com.hdsinh.cardealer.entities.CartItem;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Long, CartItem> items = new HashMap<>();

    public void addItem(CartItem item) {
        if (items.containsKey(item.getProductId())) {
            CartItem existingItem = items.get(item.getProductId());
            existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
        } else {
            items.put(item.getProductId(), item);
        }
    }

    public Map<Long, CartItem> getItems() {
        return items;
    }
    
    public void removeItem(Long productId) {
        items.remove(productId);
    }

    public double getTotal() {
        double total = 0;
        for (CartItem item : items.values()) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }
}
