package com.example.favoriteproducts.controller;

import com.example.favoriteproducts.dto.ProductDTO;
import com.example.favoriteproducts.service.ProductService;
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
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
@Tag(name = "Produtos", description = "Operações relacionadas aos produtos")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    @Operation(summary = "Listar todos os produtos", description = "Retorna uma lista de todos os produtos")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID", description = "Retorna um produto específico pelo ID")
    public ResponseEntity<ProductDTO> getProductById(@Parameter(description = "ID do produto") @PathVariable Long id) {
        Optional<ProductDTO> product = productService.findById(id);
        return product.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProductDTO>> getProductsByUserId(@PathVariable Long userId) {
        List<ProductDTO> products = productService.findByUserId(userId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategoryId(@PathVariable Long categoryId) {
        List<ProductDTO> products = productService.findByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/user/{userId}/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductsByUserIdAndCategoryId(
            @PathVariable Long userId, 
            @PathVariable Long categoryId) {
        List<ProductDTO> products = productService.findByUserIdAndCategoryId(userId, categoryId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/user/{userId}/category/name/{categoryName}")
    public ResponseEntity<List<ProductDTO>> getProductsByUserIdAndCategoryName(
            @PathVariable Long userId, 
            @PathVariable String categoryName) {
        List<ProductDTO> products = productService.findByUserIdAndCategoryName(userId, categoryName);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProductsByName(@RequestParam String name) {
        List<ProductDTO> products = productService.findByNameContaining(name);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/user/{userId}/search")
    public ResponseEntity<List<ProductDTO>> searchProductsByUserIdAndName(
            @PathVariable Long userId, 
            @RequestParam String name) {
        List<ProductDTO> products = productService.findByUserIdAndNameContaining(userId, name);
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        try {
            ProductDTO createdProduct = productService.create(productDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        try {
            ProductDTO updatedProduct = productService.update(id, productDTO);
            return ResponseEntity.ok(updatedProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> checkProductExists(@PathVariable Long id) {
        boolean exists = productService.existsById(id);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Long> getProductCountByUserId(@PathVariable Long userId) {
        Long count = productService.countByUserId(userId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/category/{categoryId}/count")
    public ResponseEntity<Long> getProductCountByCategoryId(@PathVariable Long categoryId) {
        Long count = productService.countByCategoryId(categoryId);
        return ResponseEntity.ok(count);
    }
} 