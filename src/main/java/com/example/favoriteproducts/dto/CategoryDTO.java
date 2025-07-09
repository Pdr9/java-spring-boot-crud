package com.example.favoriteproducts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class CategoryDTO {
    
    private Long id;
    
    @NotBlank(message = "Nome da categoria é obrigatório")
    @Size(min = 2, max = 50, message = "Nome da categoria deve ter entre 2 e 50 caracteres")
    private String name;
    
    @Size(max = 200, message = "Descrição deve ter no máximo 200 caracteres")
    private String description;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructors
    public CategoryDTO() {}
    
    public CategoryDTO(Long id, String name, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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
} 