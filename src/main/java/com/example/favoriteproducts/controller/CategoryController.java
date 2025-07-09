package com.example.favoriteproducts.controller;

import com.example.favoriteproducts.dto.CategoryDTO;
import com.example.favoriteproducts.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
@Tag(name = "Categorias", description = "Operações relacionadas às categorias")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    @Operation(summary = "Listar todas as categorias", description = "Retorna uma lista de todas as categorias")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar categoria por ID", description = "Retorna uma categoria específica pelo ID")
    public ResponseEntity<CategoryDTO> getCategoryById(@Parameter(description = "ID da categoria") @PathVariable Long id) {
        Optional<CategoryDTO> category = categoryService.findById(id);
        return category.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String name) {
        Optional<CategoryDTO> category = categoryService.findByName(name);
        return category.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<CategoryDTO>> searchCategoriesByName(@RequestParam String name) {
        List<CategoryDTO> categories = categoryService.findByNameContaining(name);
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        try {
            CategoryDTO createdCategory = categoryService.create(categoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
        try {
            CategoryDTO updatedCategory = categoryService.update(id, categoryDTO);
            return ResponseEntity.ok(updatedCategory);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> checkCategoryExists(@PathVariable Long id) {
        boolean exists = categoryService.existsById(id);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/name/{name}/exists")
    public ResponseEntity<Boolean> checkCategoryNameExists(@PathVariable String name) {
        boolean exists = categoryService.existsByName(name);
        return ResponseEntity.ok(exists);
    }
} 