package com.example.favoriteproducts.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API de Produtos Favoritos")
                .version("1.0.0")
                .description("Sistema de gerenciamento de produtos favoritos")
                .contact(new Contact()
                    .name("Sistema NADIC")
                    .email("suporte@nadic.com")
                )
            )
            .servers(List.of(
                new Server()
                    .url("http://localhost:8080")
                    .description("Servidor de desenvolvimento")
            ));
    }
} 