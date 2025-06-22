# USER AUTH API

Uma REST API desenvolvida com o objetivo de aprofundar os conhecimentos em autenticação e autorização de usuários utilizando Spring Security e OAuth2.

A API permite o gerenciamento básico de usuários, simulando um sistema real e funcional com segurança.  Foi desenvolvida com foco em boas práticas de desenvolvimento, Clean Code e separação de responsabilidades através de DTOs, serviços bem definidos e uso de camadas.

#ESTRUTURA:

  # Entidades:
  - User(id,username, password,[roles])
  - Role(id, name)

 # DTOs
 - UserRequestDto: entrada de dados no cadastro.
 - UserDto: saída de dados protegida para o cliente.
 - LoginRequestDto: entrada de dados no login.
 - AccessTokenDto: saida de dados protegida do token de usuario autenticado.
 - RoleDto: saida de dados protegida para a role.
 - RoleChangeRequestDto: alteração de role com segurança.
 - PasswordResetRequestDto: alteração de senha com segurança.

Uso de DTOs permite:
- Abstração da entidade.
- Segurança (evita expor dados sensíveis como password).
- Flexibilidade no controle da entrada e saída de dados.

# Boas Práticas Aplicadas
  - Clean Code com uso de:
  - Métodos curtos e bem nomeados.
  - Separação de responsabilidades (Controller / Service / Repository).
  - Lógica encapsulada em services.
  - @Transactional para garantir atomicidade em operações críticas. 
  - Exception Handling global com mensagens amigáveis.
  -  Regras de negócio bem definidas na camada de serviço.

  # Funcionamento:
  -
        - Ao executar a API pela primeira vez, as roles ADMIN e GUEST serão criadas automaticamente.
       - Durante o cadastro de usuários:
       - O primeiro usuário registrado recebe automaticamente a role ADMIN.
       - Os demais usuários registrados recebem a role GUEST.
       - Apenas usuários com a role ADMIN podem atribuir novas roles a outros usuários.
       - Para acessar os endpoints protegidos da API, o usuário precisa estar autenticado e possuir um token JWT válido.
       - Cada usuário é responsável por:
       - Manter sua conta atualizada.
       - Atualizar sua senha.
       - Excluir sua conta, se desejar.
       - A exclusão de usuários também pode ser feita por um ADMIN.

  # Tecnologias e Ferramentas
  - java v: 17
  - Spring Boot v: 3.5.3
  - spring-security
  - oauth2
  - DevTools
  - springdoc-openapi v:2.8.9
  - MYSQL v: 9.3
  - JWT
  - lombok
  - docker

    # Segurança
    - Autenticação baseada em JWT.
    - Autorização controlada por roles (ADMIN / GUEST).
    - Senhas criptografadas com BCrypt.

    # Documentação da API
        - Acesse a documentação interativa da API através do Swagger UI:
          - http://localhost:8080/swagger-ui.html

    # Como usar (Localmente)
    - Requisitos
      - Java 17+
      - Docker e docker-compose
    #
        - Clone o repositório
        git clone https://github.com/maxiamikel19/user-api-auth.git

        - Acesse a pasta do projeto
        cd user-auth-api

        - Configure o banco de dados em application.properties
        spring.datasource.url=jdbc:mysql://localhost:3306/user_auth_db
        spring.datasource.username=seu_usuario
        spring.datasource.password=sua_senha

        - Execute o projeto
            - docker compose up
            - ./mvnw spring-boot:run

    # Principais Endpoints
        - POST         /api/auth/register
        - POST          /api/auth/login
        - GET           /api/users
        - GET           /api/users/find/{id}
        - DELETE        /api/users/find/{id}/delete
        - PUT           /api/users/user/{id}/reset-password
        - PUT           /api/users/user/{id}/upgrade

  ## TESTES DEMOSTRATIVOS

  - POST         /api/auth/register
  ![image](https://github.com/user-attachments/assets/b51bd176-a867-41bb-810f-8fa4e4c81066)

  ![image](https://github.com/user-attachments/assets/4ba10eb5-2504-41b7-b06c-cb71e49eebf5)


  - POST          /api/auth/login
  ![image](https://github.com/user-attachments/assets/002e6094-7c83-4d77-b318-b7b3eb70b57b)

   - GET           /api/users
  ![image](https://github.com/user-attachments/assets/dca1def1-8c1d-4459-a771-967aa0215b20)

  - GET           /api/users/find/{id}
  ![image](https://github.com/user-attachments/assets/4a89c5a2-09f7-49fb-9960-18ce6d42b7e5)

  - DELETE        /api/users/find/{id}/delete
  ![image](https://github.com/user-attachments/assets/27dd2af2-66bc-423a-8bea-3f255e8e6a10)

 - PUT           /api/users/user/{id}/reset-password
  ![image](https://github.com/user-attachments/assets/e8581076-bce7-4753-849a-9e56a0b0ce05)

 - PUT           /api/users/user/{id}/upgrade
  - ANTES
    ![image](https://github.com/user-attachments/assets/c3fff6c7-1203-4e4b-8c09-7c8b167078b3)
 - DEPOIS
   ![image](https://github.com/user-attachments/assets/dd339579-efa1-4c46-abf1-af5e9fa836ec)










