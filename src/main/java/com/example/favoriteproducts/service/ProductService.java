package com.example.favoriteproducts.service;

import com.example.favoriteproducts.dto.ProductDTO;
import com.example.favoriteproducts.entity.Category;
import com.example.favoriteproducts.entity.Product;
import com.example.favoriteproducts.entity.User;
import com.example.favoriteproducts.mapper.ProductMapper;
import com.example.favoriteproducts.repository.CategoryRepository;
import com.example.favoriteproducts.repository.ProductRepository;
import com.example.favoriteproducts.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductMapper productMapper;

    public List<ProductDTO> findAll() {
        List<Product> products = productRepository.findAll();
        return productMapper.toDTOList(products);
    }

    public Optional<ProductDTO> findById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDTO);
    }

    public List<ProductDTO> findByUserId(Long userId) {
        List<Product> products = productRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return productMapper.toDTOList(products);
    }

    public List<ProductDTO> findByCategoryId(Long categoryId) {
        List<Product> products = productRepository.findByCategoryIdOrderByCreatedAtDesc(categoryId);
        return productMapper.toDTOList(products);
    }

    public List<ProductDTO> findByUserIdAndCategoryId(Long userId, Long categoryId) {
        List<Product> products = productRepository.findByUserIdAndCategoryId(userId, categoryId);
        return productMapper.toDTOList(products);
    }

    public List<ProductDTO> findByUserIdAndCategoryName(Long userId, String categoryName) {
        List<Product> products = productRepository.findByUserIdAndCategoryName(userId, categoryName);
        return productMapper.toDTOList(products);
    }

    public List<ProductDTO> findByNameContaining(String name) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        return productMapper.toDTOList(products);
    }

    public List<ProductDTO> findByUserIdAndNameContaining(Long userId, String name) {
        List<Product> products = productRepository.findByUserIdAndNameContainingIgnoreCase(userId, name);
        return productMapper.toDTOList(products);
    }

    public ProductDTO create(ProductDTO productDTO) {
        // Validar se o usuário existe
        User user = userRepository.findById(productDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + productDTO.getUserId()));

        // Validar se a categoria existe
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada com ID: " + productDTO.getCategoryId()));

        Product product = productMapper.toEntity(productDTO);
        product.setUser(user);
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    public ProductDTO update(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com ID: " + id));

        // Validar se o usuário existe (se foi alterado)
        if (!existingProduct.getUser().getId().equals(productDTO.getUserId())) {
            User user = userRepository.findById(productDTO.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + productDTO.getUserId()));
            existingProduct.setUser(user);
        }

        // Validar se a categoria existe (se foi alterada)
        if (!existingProduct.getCategory().getId().equals(productDTO.getCategoryId())) {
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada com ID: " + productDTO.getCategoryId()));
            existingProduct.setCategory(category);
        }

        productMapper.updateEntityFromDTO(productDTO, existingProduct);
        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toDTO(updatedProduct);
    }

    public void deleteById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Produto não encontrado com ID: " + id);
        }
        productRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return productRepository.existsById(id);
    }

    public Long countByUserId(Long userId) {
        return productRepository.countByUserId(userId);
    }

    public Long countByCategoryId(Long categoryId) {
        return productRepository.countByCategoryId(categoryId);
    }
} 