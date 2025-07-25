package com.example.favoriteproducts.repository;

import com.example.favoriteproducts.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    Optional<Category> findByName(String name);
    
    boolean existsByName(String name);
    
    @Query("SELECT c FROM Category c WHERE c.name LIKE %:name%")
    List<Category> findByNameContainingIgnoreCase(@Param("name") String name);
} 