package com.shopease.controller;

import com.shopease.model.Product;
import com.shopease.service.CartService;
import com.shopease.service.GroqService;
import com.shopease.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class ProductController {

    private final ProductService productService;
    private final CartService cartService;
    private final GroqService groqService;

    public ProductController(ProductService productService, CartService cartService, GroqService groqService) {
        this.productService = productService;
        this.cartService = cartService;
        this.groqService = groqService;
    }

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("cartItemCount", cartService.getCartItemCount(session));
        return "products";
    }

    @PostMapping("/recommendations")
    @ResponseBody
    public Map<String, String> getRecommendations(@RequestBody Map<String, String> request) {
        String query = request.get("query");
        List<Product> inventory = productService.getAllProducts();
        String recommendation = groqService.getRecommendations(query, inventory);
        return Map.of("recommendation", recommendation);
    }
}
