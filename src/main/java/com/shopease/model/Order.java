package com.shopease.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @Column(nullable = false)
    private double totalAmount;

    @Column(nullable = false)
    private String status;

    @Column(length = 2000)
    private String itemsSummary; // Store a text summary of ordered items

    public Order() {}

    public Order(LocalDateTime orderDate, double totalAmount, String status, String itemsSummary) {
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.itemsSummary = itemsSummary;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getItemsSummary() { return itemsSummary; }
    public void setItemsSummary(String itemsSummary) { this.itemsSummary = itemsSummary; }
}
