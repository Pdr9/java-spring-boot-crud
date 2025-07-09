package com.example.favoriteproducts.controller.web;

import com.example.favoriteproducts.dto.ProductDTO;
import com.example.favoriteproducts.service.CategoryService;
import com.example.favoriteproducts.service.ProductService;
import com.example.favoriteproducts.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/web/products")
public class ProductWebController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String listProducts(@RequestParam(required = false) Long userId,
                             @RequestParam(required = false) Long categoryId,
                             @RequestParam(required = false) String search,
                             Model model) {
        
        List<ProductDTO> products;
        
        if (userId != null && categoryId != null) {
            products = productService.findByUserIdAndCategoryId(userId, categoryId);
        } else if (userId != null) {
            if (search != null && !search.trim().isEmpty()) {
                products = productService.findByUserIdAndNameContaining(userId, search);
            } else {
                products = productService.findByUserId(userId);
            }
        } else if (categoryId != null) {
            products = productService.findByCategoryId(categoryId);
        } else if (search != null && !search.trim().isEmpty()) {
            products = productService.findByNameContaining(search);
        } else {
            products = productService.findAll();
        }
        
        model.addAttribute("products", products);
        model.addAttribute("users", userService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("selectedUserId", userId);
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("searchTerm", search);
        
        return "products/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new ProductDTO());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("isEdit", false);
        return "products/form";
    }

    @PostMapping
    public String createProduct(@Valid @ModelAttribute("product") ProductDTO productDTO, 
                              BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("users", userService.findAll());
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("isEdit", false);
            return "products/form";
        }
        
        try {
            productService.create(productDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Produto criado com sucesso!");
            return "redirect:/web/products";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("users", userService.findAll());
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("isEdit", false);
            return "products/form";
        }
    }

    @GetMapping("/{id}")
    public String showProduct(@PathVariable Long id, Model model) {
        Optional<ProductDTO> productOpt = productService.findById(id);
        if (productOpt.isEmpty()) {
            return "redirect:/web/products";
        }
        
        model.addAttribute("product", productOpt.get());
        return "products/view";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<ProductDTO> productOpt = productService.findById(id);
        if (productOpt.isEmpty()) {
            return "redirect:/web/products";
        }
        
        model.addAttribute("product", productOpt.get());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("isEdit", true);
        return "products/form";
    }

    @PostMapping("/{id}")
    public String updateProduct(@PathVariable Long id, @Valid @ModelAttribute("product") ProductDTO productDTO, 
                              BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("users", userService.findAll());
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("isEdit", true);
            return "products/form";
        }
        
        try {
            productService.update(id, productDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Produto atualizado com sucesso!");
            return "redirect:/web/products";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("users", userService.findAll());
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("isEdit", true);
            return "products/form";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Produto deletado com sucesso!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/web/products";
    }
} 