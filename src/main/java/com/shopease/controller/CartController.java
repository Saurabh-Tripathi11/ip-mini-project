package com.shopease.controller;

import com.shopease.model.Product;
import com.shopease.service.CartService;
import com.shopease.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping
    public String viewCart(Model model, HttpSession session) {
        model.addAttribute("cartItems", cartService.getCart(session));
        model.addAttribute("cartTotal", cartService.getCartTotal(session));
        model.addAttribute("cartItemCount", cartService.getCartItemCount(session));
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId,
                            @RequestParam(defaultValue = "1") int quantity,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        Optional<Product> product = productService.getProductById(productId);
        if (product.isPresent()) {
            cartService.addToCart(session, product.get(), quantity);
            redirectAttributes.addFlashAttribute("successMessage",
                    product.get().getName() + " added to cart!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Product not found.");
        }
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateQuantity(@RequestParam Long productId,
                                  @RequestParam int quantity,
                                  HttpSession session) {
        cartService.updateQuantity(session, productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long productId,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        cartService.removeFromCart(session, productId);
        redirectAttributes.addFlashAttribute("successMessage", "Item removed from cart.");
        return "redirect:/cart";
    }
}
