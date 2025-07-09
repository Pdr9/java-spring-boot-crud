package com.example.favoriteproducts.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "API Info", description = "Informações gerais da API")
public class ApiInfoController {

    @GetMapping
    @Operation(summary = "Informações da API", description = "Retorna informações gerais sobre a API")
    public Map<String, String> getApiInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("message", "Bem-vindo à API de Produtos Favoritos!");
        info.put("version", "1.0.0");
        info.put("documentation", "/swagger-ui.html");
        info.put("resources", "/api/categories, /api/products, /api/users");
        return info;
    }
} 