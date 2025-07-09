package com.example.favoriteproducts.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome do produto é obrigatório")
    @Size(min = 2, max = 100, message = "Nome do produto deve ter entre 2 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String name;
    
    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    @Column(length = 500)
    private String description;
    
    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    @Size(max = 200, message = "URL da imagem deve ter no máximo 200 caracteres")
    @Column(name = "image_url", length = 200)
    private String imageUrl;
    
    @Size(max = 300, message = "URL do produto deve ter no máximo 300 caracteres")
    @Column(name = "product_url", length = 300)
    private String productUrl;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "Usuário é obrigatório")
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @NotNull(message = "Categoria é obrigatória")
    private Category category;
    
    // Constructors
    public Product() {}
    
    public Product(String name, String description, BigDecimal price, User user, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.user = user;
        this.category = category;
    }
    
    // Lifecycle methods
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public String getProductUrl() {
        return productUrl;
    }
    
    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Category getCategory() {
        return category;
    }
    
    public void setCategory(Category category) {
        this.category = category;
    }
} 