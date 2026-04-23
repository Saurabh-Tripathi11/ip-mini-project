package com.shopease.service;

import com.shopease.model.CartItem;
import com.shopease.model.Product;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private static final String CART_SESSION_KEY = "SHOPPING_CART";

    @SuppressWarnings("unchecked")
    public List<CartItem> getCart(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        return cart;
    }

    public void addToCart(HttpSession session, Product product, int quantity) {
        List<CartItem> cart = getCart(session);

        // Check if product already exists in cart
        Optional<CartItem> existingItem = cart.stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem(product, quantity);
            newItem.setId((long) (cart.size() + 1));
            cart.add(newItem);
        }

        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public void updateQuantity(HttpSession session, Long productId, int quantity) {
        List<CartItem> cart = getCart(session);

        cart.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    if (quantity <= 0) {
                        cart.remove(item);
                    } else {
                        item.setQuantity(quantity);
                    }
                });

        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public void removeFromCart(HttpSession session, Long productId) {
        List<CartItem> cart = getCart(session);
        cart.removeIf(item -> item.getProduct().getId().equals(productId));
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public double getCartTotal(HttpSession session) {
        return getCart(session).stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
    }

    public int getCartItemCount(HttpSession session) {
        return getCart(session).stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    public void clearCart(HttpSession session) {
        session.removeAttribute(CART_SESSION_KEY);
    }
}
