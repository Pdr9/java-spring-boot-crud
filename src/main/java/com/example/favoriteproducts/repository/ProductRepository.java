package com.example.favoriteproducts.repository;

import com.example.favoriteproducts.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    List<Product> findByUserId(Long userId);
    
    List<Product> findByCategoryId(Long categoryId);
    
    List<Product> findByUserIdAndCategoryId(Long userId, Long categoryId);
    
    @Query("SELECT p FROM Product p WHERE p.user.id = :userId AND p.category.name = :categoryName")
    List<Product> findByUserIdAndCategoryName(@Param("userId") Long userId, @Param("categoryName") String categoryName);
    
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
    List<Product> findByNameContainingIgnoreCase(@Param("name") String name);
    
    @Query("SELECT p FROM Product p WHERE p.user.id = :userId AND p.name LIKE %:name%")
    List<Product> findByUserIdAndNameContainingIgnoreCase(@Param("userId") Long userId, @Param("name") String name);
    
    @Query("SELECT p FROM Product p WHERE p.user.id = :userId ORDER BY p.createdAt DESC")
    List<Product> findByUserIdOrderByCreatedAtDesc(@Param("userId") Long userId);
    
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId ORDER BY p.createdAt DESC")
    List<Product> findByCategoryIdOrderByCreatedAtDesc(@Param("categoryId") Long categoryId);
    
    @Query("SELECT COUNT(p) FROM Product p WHERE p.user.id = :userId")
    Long countByUserId(@Param("userId") Long userId);
    
    @Query("SELECT COUNT(p) FROM Product p WHERE p.category.id = :categoryId")
    Long countByCategoryId(@Param("categoryId") Long categoryId);
} 