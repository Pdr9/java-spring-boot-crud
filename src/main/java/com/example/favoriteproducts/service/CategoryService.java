package com.example.favoriteproducts.service;

import com.example.favoriteproducts.dto.CategoryDTO;
import com.example.favoriteproducts.entity.Category;
import com.example.favoriteproducts.mapper.CategoryMapper;
import com.example.favoriteproducts.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    public List<CategoryDTO> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toDTOList(categories);
    }

    public Optional<CategoryDTO> findById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDTO);
    }

    public Optional<CategoryDTO> findByName(String name) {
        return categoryRepository.findByName(name)
                .map(categoryMapper::toDTO);
    }

    public List<CategoryDTO> findByNameContaining(String name) {
        List<Category> categories = categoryRepository.findByNameContainingIgnoreCase(name);
        return categoryMapper.toDTOList(categories);
    }

    public CategoryDTO create(CategoryDTO categoryDTO) {
        if (categoryRepository.existsByName(categoryDTO.getName())) {
            throw new IllegalArgumentException("Categoria já existe com o nome: " + categoryDTO.getName());
        }
        
        Category category = categoryMapper.toEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDTO(savedCategory);
    }

    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada com ID: " + id));

        // Verificar se o nome já está em uso por outra categoria
        if (!existingCategory.getName().equals(categoryDTO.getName()) && 
            categoryRepository.existsByName(categoryDTO.getName())) {
            throw new IllegalArgumentException("Categoria já existe com o nome: " + categoryDTO.getName());
        }

        categoryMapper.updateEntityFromDTO(categoryDTO, existingCategory);
        Category updatedCategory = categoryRepository.save(existingCategory);
        return categoryMapper.toDTO(updatedCategory);
    }

    public void deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new IllegalArgumentException("Categoria não encontrada com ID: " + id);
        }
        
        // Verificar se a categoria possui produtos antes de deletar
        if (categoryRepository.findById(id).get().getProducts().size() > 0) {
            throw new IllegalArgumentException("Não é possível deletar categoria que possui produtos associados. ID: " + id);
        }
        
        categoryRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }

    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }
} 