# PromptHub

Plataforma de gerenciamento e catalogação de prompts, instruções e chat modes para IA.

## Sobre o Projeto

PromptHub é uma API REST desenvolvida para centralizar e organizar prompts de IA, permitindo que times e usuários criem, compartilhem e gerenciem catálogos de prompts, instruções e configurações de chat modes. O sistema oferece controle de acesso baseado em times, versionamento e auditoria completa.

## Tecnologias

- **Java 17**
- **Spring Boot 3.3.8**
- **Spring Data JPA** - Persistência de dados
- **PostgreSQL** - Banco de dados
- **Flyway** - Versionamento de banco de dados
- **ModelMapper** - Mapeamento de objetos
- **Lombok** - Redução de boilerplate
- **SpringDoc OpenAPI** - Documentação da API
- **Maven** - Gerenciamento de dependências

## Funcionalidades

### Gerenciamento de Prompts
- Criação e catalogação de prompts para diferentes casos de uso
- Versionamento de prompts
- Categorização e tags
- Busca e filtros avançados

### Controle de Acesso
- Gerenciamento de usuários e times
- Permissões baseadas em times
- Compartilhamento de prompts entre times
- Controle de visibilidade (público/privado)

### Auditoria e Rastreabilidade
- Registro automático de criação e modificação
- Histórico de alterações
- Controle de status (ativo/inativo)

## Estrutura do Projeto

```
src/main/java/br/com/senior/prompthub/
├── config/          # Configurações gerais e auditoria
├── core/            # Componentes base reutilizáveis
│   ├── controller/  # Controllers base
│   ├── dto/         # DTOs genéricos (PageParams, PageResult)
│   ├── repository/  # Repositories base
│   ├── service/     # Services base e ModelMapper
│   └── specification/ # Specifications base para queries
├── domain/          # Domínio da aplicação
│   ├── controller/  # Controllers REST (Users, Teams, Prompts)
│   ├── dto/         # DTOs específicos do domínio
│   ├── entity/      # Entidades JPA (User, Team, Prompt)
│   ├── repository/  # Repositories JPA
│   ├── service/     # Serviços de negócio e validações
│   └── spec/        # Specifications para filtros e buscas
├── infrastructure/  # Infraestrutura
│   └── exception/   # Tratamento centralizado de exceções
└── utils/           # Utilitários gerais
```

## Pré-requisitos

- Java 17 ou superior
- Maven 3.6+
- PostgreSQL 12+

## Configuração

1. Clone o repositório
2. Configure o banco de dados PostgreSQL
3. Atualize as credenciais em `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/prompthub
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

4. Execute as migrations do Flyway (automático na inicialização)

## Executando o Projeto

```bash
# Compilar
mvn clean install

# Executar
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`

## Documentação da API

Após iniciar a aplicação, acesse a documentação Swagger em:

```
http://localhost:8080/swagger-ui.html
```

## Endpoints Principais

### Usuários
- `GET /api/users` - Listar usuários (com paginação e filtros)
- `GET /api/users/{id}` - Buscar usuário por ID
- `POST /api/users` - Criar usuário
- `PUT /api/users/{id}` - Atualizar usuário
- `DELETE /api/users/{id}` - Deletar usuário

### Times
- `GET /api/teams` - Listar times (com paginação e filtros)
- `GET /api/teams/{id}` - Buscar time por ID
- `POST /api/teams` - Criar time
- `PUT /api/teams/{id}` - Atualizar time
- `DELETE /api/teams/{id}` - Deletar time

### Prompts (em desenvolvimento)
- `GET /api/prompts` - Listar prompts do catálogo
- `GET /api/prompts/{id}` - Buscar prompt por ID
- `POST /api/prompts` - Criar novo prompt
- `PUT /api/prompts/{id}` - Atualizar prompt
- `DELETE /api/prompts/{id}` - Deletar prompt

## Arquitetura

O projeto segue uma arquitetura em camadas com separação clara de responsabilidades:

- **Controller**: Recebe requisições HTTP, valida entrada e retorna respostas
- **Service**: Contém lógica de negócio e orquestração
- **Repository**: Acesso a dados e queries customizadas
- **Entity**: Modelos de domínio (JPA)
- **DTO**: Objetos de transferência de dados (Request/Response)
- **Specification**: Queries dinâmicas e filtros complexos

### Recursos Especiais

- **@NoUpdateMapping**: Anotação customizada para proteger campos durante atualizações via ModelMapper (IDs, timestamps, etc)
- **Auditoria Automática**: Campos `createdAt` e `updatedAt` gerenciados pelo JPA Auditing
- **BaseService**: Classe abstrata com operações CRUD genéricas e reutilizáveis
- **CustomException**: Tratamento centralizado de exceções com respostas padronizadas
- **Paginação**: Suporte nativo a paginação e ordenação em todas as listagens
- **Specifications**: Filtros dinâmicos e queries complexas usando JPA Criteria API

## Casos de Uso

- Equipes de desenvolvimento que precisam compartilhar prompts de IA
- Catalogação de instruções para diferentes modelos de linguagem
- Gerenciamento de chat modes e configurações de conversação
- Versionamento e histórico de prompts
- Biblioteca centralizada de prompts para toda organização

## Roadmap

- [ ] Implementação completa do módulo de Prompts
- [ ] Sistema de versionamento de prompts
- [ ] Tags e categorização
- [ ] Sistema de favoritos
- [ ] Estatísticas de uso
- [ ] Exportação/Importação de catálogos
- [ ] API de busca semântica

## Licença

Este projeto é propriedade da Senior Sistemas.
