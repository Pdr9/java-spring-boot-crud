package com.example.favoriteproducts.controller.web;

import com.example.favoriteproducts.dto.CategoryDTO;
import com.example.favoriteproducts.dto.ProductDTO;
import com.example.favoriteproducts.service.CategoryService;
import com.example.favoriteproducts.service.ProductService;
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
@RequestMapping("/web/categories")
public class CategoryWebController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "categories/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new CategoryDTO());
        model.addAttribute("isEdit", false);
        return "categories/form";
    }

    @PostMapping
    public String createCategory(@Valid @ModelAttribute("category") CategoryDTO categoryDTO, 
                               BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "categories/form";
        }
        
        try {
            categoryService.create(categoryDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Categoria criada com sucesso!");
            return "redirect:/web/categories";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("isEdit", false);
            return "categories/form";
        }
    }

    @GetMapping("/{id}")
    public String showCategory(@PathVariable Long id, Model model) {
        Optional<CategoryDTO> categoryOpt = categoryService.findById(id);
        if (categoryOpt.isEmpty()) {
            return "redirect:/web/categories";
        }
        
        CategoryDTO category = categoryOpt.get();
        List<ProductDTO> products = productService.findByCategoryId(id);
        
        model.addAttribute("category", category);
        model.addAttribute("products", products);
        return "categories/view";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<CategoryDTO> categoryOpt = categoryService.findById(id);
        if (categoryOpt.isEmpty()) {
            return "redirect:/web/categories";
        }
        
        model.addAttribute("category", categoryOpt.get());
        model.addAttribute("isEdit", true);
        return "categories/form";
    }

    @PostMapping("/{id}")
    public String updateCategory(@PathVariable Long id, @Valid @ModelAttribute("category") CategoryDTO categoryDTO, 
                               BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("isEdit", true);
            return "categories/form";
        }
        
        try {
            categoryService.update(id, categoryDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Categoria atualizada com sucesso!");
            return "redirect:/web/categories";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("isEdit", true);
            return "categories/form";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            categoryService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Categoria deletada com sucesso!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/web/categories";
    }
} 