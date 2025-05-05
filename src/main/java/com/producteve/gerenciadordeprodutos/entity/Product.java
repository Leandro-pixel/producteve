package com.producteve.gerenciadordeprodutos.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ou use Integer, se preferir

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double value;

    @Column(nullable = false)
    private int estimateWasteDays;

    @Column(nullable = false)
    private LocalDate lastPurchaseDate; // Alterado para LocalDateTime

    private LocalDate reminderDate; // Novo campo para lembrar a data
    
    private String calendarEventId;

    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    // Construtores
    public Product() {}

    public Product(UUID userId, String name, double value, int estimateWasteDays,
                   LocalDate lastPurchaseDate, String calendarEventId) {
        this.userId = userId;
        this.name = name;
        this.value = value;
        this.estimateWasteDays = estimateWasteDays;
        this.lastPurchaseDate = lastPurchaseDate;
        this.calendarEventId = calendarEventId;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getEstimateWasteDays() {
        return estimateWasteDays;
    }

    public void setEstimateWasteDays(int estimateWasteDays) {
        this.estimateWasteDays = estimateWasteDays;
    }

    public LocalDate getLastPurchaseDate() {
        return lastPurchaseDate;
    }

    public void setLastPurchaseDate(LocalDate lastPurchaseDate) {
        this.lastPurchaseDate = lastPurchaseDate;
    }

    public String getCalendarEventId() {
        return calendarEventId;
    }

    public void setCalendarEventId(String calendarEventId) {
        this.calendarEventId = calendarEventId;
    }

    public LocalDateTime getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(LocalDateTime creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public LocalDate getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(LocalDate reminderDate) {
        this.reminderDate = reminderDate;
    }
}
