package com.example.favoriteproducts.controller.web;

import com.example.favoriteproducts.dto.UserDTO;
import com.example.favoriteproducts.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/web/users")
public class UserWebController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("isEdit", false);
        return "users/form";
    }

    @PostMapping
    public String createUser(@Valid @ModelAttribute("user") UserDTO userDTO, 
                           BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "users/form";
        }
        
        try {
            userService.create(userDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Usuário criado com sucesso!");
            return "redirect:/web/users";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("isEdit", false);
            return "users/form";
        }
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable Long id, Model model) {
        Optional<UserDTO> userOpt = userService.findById(id);
        if (userOpt.isEmpty()) {
            return "redirect:/web/users";
        }
        
        model.addAttribute("user", userOpt.get());
        return "users/view";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<UserDTO> userOpt = userService.findById(id);
        if (userOpt.isEmpty()) {
            return "redirect:/web/users";
        }
        
        model.addAttribute("user", userOpt.get());
        model.addAttribute("isEdit", true);
        return "users/form";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, @Valid @ModelAttribute("user") UserDTO userDTO, 
                           BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("isEdit", true);
            return "users/form";
        }
        
        try {
            userService.update(id, userDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Usuário atualizado com sucesso!");
            return "redirect:/web/users";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("isEdit", true);
            return "users/form";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Usuário deletado com sucesso!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/web/users";
    }
} 