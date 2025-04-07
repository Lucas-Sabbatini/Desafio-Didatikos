# Desafio Didatikos - API REST com Spring Boot

Este projeto é uma API REST construída com Spring Boot que implementa operações CRUD para gerenciamento de usuários, produtos e cidades, além de autenticação e autorização com JWT e Spring Security.

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **Spring Data JPA** – Acesso e persistência de dados.
- **Spring Security** – Autenticação e autorização.
- **JWT (JSON Web Tokens)** – Para autenticação sem estado.
- **Lombok** – Redução de código boilerplate.
- **Banco de Dados Relacional** – Pode ser utilizado MySQL, H2 ou outro de sua preferência.
- **Maven/Gradle** – Gerenciamento de dependências e build.

## Endpoints da Aplicação

### Autenticação
- **POST /login**  
  Endpoint responsável pela autenticação de usuários e emissão do token JWT.  
  **Exemplo de Requisição:**
  ```json
  {
    "username": "username",
    "password": "password"
  }
  ```  

### Usuários
- **GET /users**  
  Lista todos os usuários cadastrados.

- **GET /users/{id}**  
  Retorna os detalhes de um usuário específico pelo ID.

- **POST /users**  
  Cria um novo usuário.  
  **Exemplo de Requisição:**
  ```json
  {
    "name": "username",
    "password": "password",
    "cpf": "70030218110",
    "role": "ADMIN"  // ou "USER"
  }
  ```

- **PUT /users/{id}**  
  Atualiza os dados de um usuário existente.  
  **Exemplo de Requisição:**
  ```json
  {
    "name": "novoNome",
    "password": "novaSenha",
    "cpf": "70030218110",
    "role": "ADMIN"  // ou "USER"
  }
  ```

- **DELETE /users/{id}**  
  Remove um usuário pelo ID.

### Produtos
- **GET /products**  
  Lista todos os produtos cadastrados.

- **GET /products/{id}**  
  Retorna os detalhes de um produto específico pelo ID.

- **POST /products**  
  Cria um novo produto.  
  **Exemplo de Requisição (usando o DTO):**
  ```json
  {
    "name": "Produto Exemplo",
    "price": 49.99,
    "stock": 10,
    "cityId": 1
  }
  ```

- **PUT /products/{id}**  
  Atualiza um produto existente.  
  **Exemplo de Requisição:**
  ```json
  {
    "name": "Produto Atualizado",
    "price": 59.99,
    "stock": 8,
    "cityId": 2
  }
  ```

- **DELETE /products/{id}**  
  Remove um produto pelo ID.

### Cidades
- **GET /cidades**  
  Lista todas as cidades disponíveis.  
  **Exemplo de Resposta:**
  ```json
  [
      { "id": 1, "name": "São Paulo" },
      { "id": 2, "name": "Rio de Janeiro" },
      { "id": 3, "name": "Belo Horizonte" },
      { "id": 4, "name": "Brasília" },
      { "id": 5, "name": "Salvador" },
      { "id": 6, "name": "Curitiba" },
      { "id": 7, "name": "Fortaleza" },
      { "id": 8, "name": "Manaus" },
      { "id": 9, "name": "Recife" },
      { "id": 10, "name": "Porto Alegre" },
      { "id": 11, "name": "Goiânia" },
      { "id": 12, "name": "Uberlândia" },
      { "id": 13, "name": "Uberaba" }
  ]
  ```

## Configuração de Segurança

A aplicação utiliza Spring Security para proteger os endpoints. O `JwtEncoder` e `JwtDecoder` são configurados para validar os tokens JWT. As regras de autorização permitem acesso aos endpoints com base nas roles dos usuários (por exemplo, ADMIN e USER) ou através de autenticação válida.

## Execução do Projeto

1. **Clone o repositório:**
   ```bash
   git clone <url-do-repositorio>
   cd <nome-do-projeto>
   ```
3. **Crie o container MySQL**
   ```bash
   docker-compose up -d
   ```
2. **Execute o projeto:**
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Acesse os endpoints:**  
   Utilize ferramentas como Postman ou curl para testar os endpoints conforme listado acima.