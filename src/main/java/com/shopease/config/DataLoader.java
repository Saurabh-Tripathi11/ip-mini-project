package com.shopease.config;

import com.shopease.model.Product;
import com.shopease.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;

    public DataLoader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        // Electronics
        productRepository.save(new Product(
                "Sony WH-1000XM5 Headphones",
                "Industry-leading noise cancellation with exceptional sound quality. 30-hour battery life, plush ear cushions, and multipoint connection.",
                29999.00, 25,
                "https://images.unsplash.com/photo-1618366712010-f4ae9c647dcb?w=400&h=400&fit=crop"
        ));

        productRepository.save(new Product(
                "Apple iPad Air M2",
                "11-inch Liquid Retina display with M2 chip. Perfect for creative work, note-taking, and entertainment with all-day battery life.",
                59999.00, 15,
                "https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?w=400&h=400&fit=crop"
        ));

        productRepository.save(new Product(
                "Samsung Galaxy Watch 6",
                "Advanced health monitoring with BIA sensor, sleep coaching, and sapphire crystal display. Seamless smartphone integration.",
                24999.00, 30,
                "https://images.unsplash.com/photo-1579586337278-3befd40fd17a?w=400&h=400&fit=crop"
        ));

        // Lifestyle
        productRepository.save(new Product(
                "Kindle Paperwhite Signature",
                "6.8-inch glare-free display with adjustable warm light, wireless charging, and 32GB storage. Waterproof for worry-free reading.",
                14999.00, 40,
                "https://images.unsplash.com/photo-1594377157609-5c996118ac7f?w=400&h=400&fit=crop"
        ));

        productRepository.save(new Product(
                "JBL Charge 5 Speaker",
                "Portable Bluetooth speaker with powerful JBL Pro Sound, IP67 waterproof rating, and 20-hour playtime. Built-in power bank.",
                12999.00, 35,
                "https://images.unsplash.com/photo-1608043152269-423dbba4e7e1?w=400&h=400&fit=crop"
        ));

        productRepository.save(new Product(
                "Fujifilm Instax Mini 12",
                "Instant camera with automatic exposure, selfie mode, and close-up lens. Captures and prints memories in seconds.",
                8999.00, 20,
                "https://images.unsplash.com/photo-1526170375885-4d8ecf77b99f?w=400&h=400&fit=crop"
        ));

        System.out.println("✅ DataLoader: 6 sample products loaded successfully!");
    }
}
