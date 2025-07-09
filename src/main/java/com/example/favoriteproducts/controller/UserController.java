package com.example.favoriteproducts.controller;

import com.example.favoriteproducts.dto.UserDTO;
import com.example.favoriteproducts.service.UserService;
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
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
@Tag(name = "Usuários", description = "Operações relacionadas aos usuários")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista de todos os usuários")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário específico pelo ID")
    public ResponseEntity<UserDTO> getUserById(@Parameter(description = "ID do usuário") @PathVariable Long id) {
        Optional<UserDTO> user = userService.findById(id);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        Optional<UserDTO> user = userService.findByEmail(email);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        try {
            UserDTO createdUser = userService.create(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        try {
            UserDTO updatedUser = userService.update(id, userDTO);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> checkUserExists(@PathVariable Long id) {
        boolean exists = userService.existsById(id);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/email/{email}/exists")
    public ResponseEntity<Boolean> checkEmailExists(@PathVariable String email) {
        boolean exists = userService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }
} 