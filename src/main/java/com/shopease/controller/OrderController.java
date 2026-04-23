package com.shopease.controller;

import com.shopease.model.Order;
import com.shopease.service.CartService;
import com.shopease.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;

    public OrderController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @PostMapping("/checkout")
    public String checkout(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        try {
            Order order = orderService.checkout(session);
            model.addAttribute("order", order);
            model.addAttribute("cartItemCount", 0);
            return "checkout-success";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/cart";
        }
    }

    @GetMapping("/orders")
    public String viewOrders(Model model, HttpSession session) {
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("cartItemCount", cartService.getCartItemCount(session));
        return "orders";
    }

    @GetMapping("/orders/{id}")
    public String viewOrderDetail(@PathVariable Long id, Model model, HttpSession session) {
        Optional<Order> order = orderService.getOrderById(id);
        if (order.isPresent()) {
            model.addAttribute("order", order.get());
            model.addAttribute("cartItemCount", cartService.getCartItemCount(session));
            return "order-detail";
        }
        return "redirect:/orders";
    }
}
