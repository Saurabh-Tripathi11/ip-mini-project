# 🛒 ShopEase — Online Shopping Cart System

An AI-powered e-commerce web application built with Spring Boot, Thymeleaf, and the Groq API.

![Java](https://img.shields.io/badge/Java-17+-orange?style=flat-square)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4-brightgreen?style=flat-square)
![License](https://img.shields.io/badge/License-MIT-blue?style=flat-square)

## ✨ Features

- **Product Catalog** — Browse a grid of electronics & lifestyle products
- **Shopping Cart** — Add, update quantities, and remove items
- **Checkout & Orders** — Place orders and view order history
- **AI Recommendations** — Ask Groq AI for smart product suggestions
- **Premium Dark UI** — Glassmorphism design with smooth animations

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 17+, Spring Boot 3.x, Spring Data JPA |
| Database | H2 (in-memory — zero setup) |
| Frontend | Thymeleaf, Bootstrap 5 (CDN) |
| AI | Groq API (LLaMA 3.3 70B) |

## 🚀 Quick Start

### Prerequisites
- **Java 17** or higher installed ([Download](https://adoptium.net/))
- That's it! No database or build tool installation needed.

### Steps

1. **Clone / Download** the project

2. **Set your Groq API key** as an environment variable:

   **macOS / Linux:**
   ```bash
   export GROQ_API_KEY=your_api_key_here
   ```

   **Windows (Command Prompt):**
   ```cmd
   set GROQ_API_KEY=your_api_key_here
   ```

   **Windows (PowerShell):**
   ```powershell
   $env:GROQ_API_KEY="your_api_key_here"
   ```

3. **Run the application:**

   **macOS / Linux:**
   ```bash
   ./mvnw spring-boot:run
   ```

   **Windows:**
   ```cmd
   mvnw.cmd spring-boot:run
   ```

4. **Open your browser** at [http://localhost:8080](http://localhost:8080)

> The app comes pre-loaded with 6 sample products. Start shopping immediately!

## 📁 Project Structure

```
src/main/java/com/shopease/
├── ShoppingCartApplication.java    # Entry point
├── config/
│   └── DataLoader.java             # Sample data seeder
├── model/
│   ├── Product.java                # Product entity
│   ├── CartItem.java               # Cart item entity
│   └── Order.java                  # Order entity
├── repository/
│   ├── ProductRepository.java
│   └── OrderRepository.java
├── service/
│   ├── ProductService.java
│   ├── CartService.java            # Session-based cart
│   ├── OrderService.java
│   └── GroqService.java            # AI integration
└── controller/
    ├── ProductController.java
    ├── CartController.java
    └── OrderController.java

src/main/resources/
├── application.properties
├── static/css/style.css
└── templates/
    ├── products.html               # Home + AI search
    ├── cart.html                    # Shopping cart
    ├── checkout-success.html       # Order confirmation
    ├── orders.html                 # Order history
    └── order-detail.html           # Single order view
```

## 🤖 AI Integration

The app integrates with the **Groq API** to provide intelligent product recommendations:

1. Type a query in the "Ask Groq for Recommendations" search bar
2. The system sends your query + the full product inventory to Groq
3. Groq returns personalized recommendations based on available products

> **Note:** The AI feature requires a valid `GROQ_API_KEY`. Get one free at [console.groq.com](https://console.groq.com)

## 🗄️ Database

Uses **H2 in-memory database** — no installation or configuration needed. Data resets on restart.

Access the H2 Console at: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- JDBC URL: `jdbc:h2:mem:shopease`
- Username: `sa`
- Password: *(leave empty)*

## 📄 License

MIT License — feel free to use and modify.
