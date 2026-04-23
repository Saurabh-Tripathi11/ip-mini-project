package com.shopease.service;

import com.shopease.model.CartItem;
import com.shopease.model.Order;
import com.shopease.repository.OrderRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;

    public OrderService(OrderRepository orderRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
    }

    public Order checkout(HttpSession session) {
        List<CartItem> cartItems = cartService.getCart(session);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty. Cannot place an order.");
        }

        double total = cartService.getCartTotal(session);

        // Build items summary
        String itemsSummary = cartItems.stream()
                .map(item -> item.getProduct().getName() + " x" + item.getQuantity()
                        + " — ₹" + String.format("%.2f", item.getSubtotal()))
                .collect(Collectors.joining(" | "));

        Order order = new Order(LocalDateTime.now(), total, "CONFIRMED", itemsSummary);
        Order savedOrder = orderRepository.save(order);

        // Clear the cart after successful order
        cartService.clearCart(session);

        return savedOrder;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAllByOrderByOrderDateDesc();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
}
