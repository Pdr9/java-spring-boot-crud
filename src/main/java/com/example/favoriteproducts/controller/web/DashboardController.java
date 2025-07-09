package com.example.favoriteproducts.controller.web;

import com.example.favoriteproducts.service.CategoryService;
import com.example.favoriteproducts.service.ProductService;
import com.example.favoriteproducts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("totalUsers", userService.findAll().size());
        model.addAttribute("totalCategories", categoryService.findAll().size());
        model.addAttribute("totalProducts", productService.findAll().size());
        model.addAttribute("recentProducts", productService.findAll().stream().limit(5).toList());
        
        return "dashboard";
    }
} 