# 💖 Sistema de Gerenciamento de Produtos Favoritos

Bem-vindo ao sistema de gerenciamento de produtos favoritos! Este projeto é uma plataforma para organizar e gerenciar seus produtos preferidos, permitindo a criação de categorias, o registro de produtos com detalhes e a associação a usuários.

## ✨ Funcionalidades

*   **Gerenciamento de Usuários:** Crie, visualize, atualize e exclua usuários.
*   **Gerenciamento de Categorias:** Crie, visualize, atualize e exclua categorias para organizar seus produtos.
*   **Gerenciamento de Produtos:** Adicione, visualize, atualize e exclua produtos, associando-os a usuários e categorias.
*   **API RESTful:** Endpoints para integração com outras aplicações.
*   **Documentação Swagger/OpenAPI:** Interface interativa para explorar e testar a API.
*   **Interface Web (Thymeleaf):** Páginas HTML para gerenciamento via navegador.
*   **Validação de Dados:** Garante a integridade dos dados.
*   **Migrações de Banco de Dados:** Utiliza Flyway para gerenciar o esquema do banco de dados.

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas, promovendo a separação de responsabilidades e a manutenibilidade:

*   **Controller:** Camada de apresentação, responsável por receber requisições HTTP e retornar respostas. Inclui `RestController` para a API e controladores para a interface web.
*   **Service:** Camada de lógica de negócio, onde as regras de negócio são implementadas e as operações de serviço são coordenadas.
*   **Repository:** Camada de acesso a dados, responsável pela interação direta com o banco de dados via Spring Data JPA.
*   **Entity:** Representa as entidades do banco de dados, mapeadas para tabelas no esquema.
*   **DTO (Data Transfer Object):** Objetos de transferência de dados, utilizados para trafegar dados entre as camadas, protegendo as entidades e otimizando a comunicação.
*   **Mapper:** Responsável pela conversão entre entidades e DTOs, utilizando MapStruct para facilitar esse processo.

## 🛠️ Tecnologias Utilizadas

### Backend
*   **Java 21** ☕: Linguagem de programação principal.
*   **Spring Boot 3.2.0** 🍃: Framework robusto para o desenvolvimento rápido de aplicações Spring.
*   **Spring Data JPA** 💾: Simplifica o acesso a dados e a implementação de repositórios baseados em JPA.
*   **Spring Web** 🌐: Suporte para desenvolvimento de aplicações web, incluindo APIs RESTful e aplicações baseadas em MVC.
*   **Spring Boot Validation** ✅: Facilita a validação de dados usando anotações JSR 303 (Bean Validation).

### Documentação
*   **SpringDoc OpenAPI 3** 📚: Geração automática de documentação da API com interface Swagger UI.

### Banco de Dados
*   **MySQL 8.0** 💧: Sistema de gerenciamento de banco de dados relacional.
*   **Flyway** ✈️: Ferramenta de migração de banco de dados, gerencia e aplica scripts SQL de forma versionada.

### Mapeamento
*   **MapStruct** 🗺️: Gerador de código que simplifica a implementação de mappers entre JavaBeans, evitando mapeamentos manuais e repetitivos.

### Frontend (Web)
*   **Thymeleaf** 🌿: Motor de template server-side para renderização de páginas HTML dinâmicas.
*   **HTML, CSS, JavaScript** 💻: Tecnologias fundamentais para a construção da interface de usuário.

### Ferramentas de Construção e Containerização
*   **Apache Maven** ⚙️: Ferramenta de automação de construção e gerenciamento de projetos Java.
*   **Docker & Docker Compose** 🐳: Plataforma para desenvolver, enviar e executar aplicações em contêineres, facilitando a configuração do ambiente de banco de dados.

## 🚀 Instalação e Execução

Siga os passos abaixo para configurar e rodar o projeto em sua máquina local.

### Pré-requisitos

Certifique-se de ter os seguintes softwares instalados:

*   **Java Development Kit (JDK) 21** ou superior
*   **Apache Maven**
*   **Docker Desktop** (para rodar o MySQL facilmente) 🐳

### 1. Clonar o Repositório

```bash
git clone <URL_DO_SEU_REPOSITORIO>
cd PROJETO\ NADIC
```

### 2. Configurar o Banco de Dados com Docker

**⚠️ IMPORTANTE:** Certifique-se de que o Docker Desktop esteja rodando antes de continuar.

No diretório raiz do projeto (onde está o `docker-compose.yml`), execute:

```bash
docker-compose up -d
```

Este comando irá iniciar um contêiner MySQL na porta `3306`. As credenciais são:
*   **Usuário:** `root`
*   **Senha:** `admin`
*   **Banco de Dados:** `favorite_products`

### 3. Compilar e Rodar a Aplicação

No diretório raiz do projeto, compile e execute a aplicação Spring Boot:

```bash
mvn clean compile
mvn spring-boot:run
```

A aplicação será iniciada na porta `8080`.

### 4. Verificar se a aplicação está rodando

Acesse: [http://localhost:8080/api](http://localhost:8080/api)

Você deve ver uma resposta JSON similar a:
```json
{
  "message": "Bem-vindo à API de Produtos Favoritos!",
  "version": "1.0.0",
  "documentation": "/swagger-ui.html",
  "resources": "/api/categories, /api/products, /api/users"
}
```

## 📚 Documentação da API

### Swagger UI (Recomendado)

A documentação interativa da API está disponível em:
**[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

A interface do Swagger permite:
- Visualizar todos os endpoints disponíveis
- Testar requisições diretamente no navegador
- Ver exemplos de request/response
- Explorar os modelos de dados (DTOs)

### Endpoints da API (JSON)

A especificação OpenAPI em formato JSON está disponível em:
**[http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)**

## 📋 Principais Endpoints da API

A API REST está disponível no prefixo `/api`. Todos os endpoints retornam dados em formato JSON.

### 🏠 Informações da API
- **`GET /api`** - Informações básicas sobre a API

### 👥 Usuários (`/api/users`)
- **`GET /api/users`** - Lista todos os usuários
- **`GET /api/users/{id}`** - Busca usuário por ID
- **`GET /api/users/email/{email}`** - Busca usuário por email
- **`POST /api/users`** - Cria novo usuário
- **`PUT /api/users/{id}`** - Atualiza usuário
- **`DELETE /api/users/{id}`** - Deleta usuário

### 📂 Categorias (`/api/categories`)
- **`GET /api/categories`** - Lista todas as categorias
- **`GET /api/categories/{id}`** - Busca categoria por ID
- **`GET /api/categories/name/{name}`** - Busca categoria por nome
- **`GET /api/categories/search?name={name}`** - Pesquisa categorias
- **`POST /api/categories`** - Cria nova categoria
- **`PUT /api/categories/{id}`** - Atualiza categoria
- **`DELETE /api/categories/{id}`** - Deleta categoria

### 🛍️ Produtos (`/api/products`)
- **`GET /api/products`** - Lista todos os produtos
- **`GET /api/products/{id}`** - Busca produto por ID
- **`GET /api/products/user/{userId}`** - Lista produtos de um usuário
- **`GET /api/products/category/{categoryId}`** - Lista produtos de uma categoria
- **`GET /api/products/search?name={name}`** - Pesquisa produtos
- **`POST /api/products`** - Cria novo produto
- **`PUT /api/products/{id}`** - Atualiza produto
- **`DELETE /api/products/{id}`** - Deleta produto

## 🧪 Testando a API

### Arquivo de Teste HTTP

O projeto inclui o arquivo `test-endpoints-api.http` com exemplos de requisições para todos os endpoints. Use este arquivo com:
- **IntelliJ IDEA** (suporte nativo)
- **Visual Studio Code** (extensão REST Client)
- **Insomnia** ou **Postman** (importação)

### Exemplos de Uso

```bash
# Informações da API
curl http://localhost:8080/api

# Listar usuários
curl http://localhost:8080/api/users

# Criar nova categoria
curl -X POST -H "Content-Type: application/json" \
  -d '{"name":"Eletrônicos","description":"Produtos eletrônicos"}' \
  http://localhost:8080/api/categories

# Criar novo usuário
curl -X POST -H "Content-Type: application/json" \
  -d '{"name":"João Silva","email":"joao@email.com"}' \
  http://localhost:8080/api/users

# Criar novo produto
curl -X POST -H "Content-Type: application/json" \
  -d '{"name":"Smartphone","description":"Smartphone Android","price":999.99,"userId":1,"categoryId":1}' \
  http://localhost:8080/api/products
```

## 🖥️ Interface Web

### Dashboard Principal

Acesse: [http://localhost:8080/](http://localhost:8080/)

A interface web oferece:
- Dashboard com estatísticas
- Páginas para gerenciar usuários, categorias e produtos
- Formulários para criar/editar registros
- Listagens com filtros e busca

## 🔧 Troubleshooting

### Problema: Erro 500 "Erro inesperado"

**Causa:** Geralmente indica que o banco de dados não está disponível.

**Solução:**
1. Verifique se o Docker Desktop está rodando
2. Execute `docker-compose up -d` para iniciar o MySQL
3. Aguarde alguns segundos para o banco inicializar
4. Reinicie a aplicação com `mvn spring-boot:run`

### Problema: "error during connect" no Docker

**Causa:** Docker Desktop não está iniciado.

**Solução:**
1. Abra o Docker Desktop
2. Aguarde até aparecer "Docker Desktop is running" na bandeja do sistema
3. Execute `docker-compose up -d`

### Problema: Aplicação não conecta ao banco

**Solução:**
1. Verifique se o MySQL está rodando: `docker ps`
2. Teste a conexão: `docker exec -it <container_id> mysql -u root -p`
3. Verifique as configurações em `application.properties`

### Problema: Porta 8080 já está em uso

**Solução:**
1. Pare outros serviços na porta 8080
2. Ou altere a porta em `application.properties`:
   ```properties
   server.port=8081
   ```

## 🔄 Migrações do Banco de Dados

O projeto utiliza **Flyway** para controle de versão do banco de dados. As migrações estão em:
```
src/main/resources/db/migration/
├── V1__Create_users_table.sql
├── V2__Create_categories_table.sql
└── V3__Create_products_table.sql
```

As migrações são executadas automaticamente quando a aplicação inicia.

## 📁 Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/example/favoriteproducts/
│   │   ├── config/          # Configurações (Swagger, Web)
│   │   ├── controller/      # Controladores REST e Web
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── entity/          # Entidades JPA
│   │   ├── exception/       # Tratamento de exceções
│   │   ├── mapper/          # Mappers MapStruct
│   │   ├── repository/      # Repositórios Spring Data
│   │   └── service/         # Serviços de negócio
│   └── resources/
│       ├── db/migration/    # Scripts Flyway
│       ├── static/          # Arquivos estáticos (CSS, JS)
│       ├── templates/       # Templates Thymeleaf
│       └── application.properties
├── docker-compose.yml       # Configuração do MySQL
├── test-endpoints-api.http  # Arquivo de teste da API
└── README.md
```

## 🚀 Próximos Passos

- [ ] Implementar autenticação e autorização
- [ ] Adicionar testes unitários e de integração
- [ ] Implementar paginação nos endpoints
- [ ] Adicionar filtros avançados
- [ ] Criar API para upload de imagens de produtos
- [ ] Implementar cache com Redis
- [ ] Documentar mais endpoints no Swagger

## 📝 Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

---

**Sistema desenvolvido para o projeto NADIC** 💙