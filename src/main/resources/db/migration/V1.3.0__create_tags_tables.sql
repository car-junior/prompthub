-- Sequences
CREATE SEQUENCE IF NOT EXISTS dbo.tags_seq;
CREATE SEQUENCE IF NOT EXISTS dbo.prompt_version_tags_seq;

-- Tabela de tags
CREATE TABLE IF NOT EXISTS dbo.tags
(
    id          INTEGER PRIMARY KEY DEFAULT nextval('dbo.tags_seq'),
    name        VARCHAR(50)  NOT NULL UNIQUE,
    slug        VARCHAR(50)  NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at  TIMESTAMP,
    updated_at  TIMESTAMP
);

-- Tabela de relacionamento prompt_version-tag (many-to-many)
CREATE TABLE IF NOT EXISTS dbo.prompt_version_tags
(
    id                 INTEGER PRIMARY KEY DEFAULT nextval('dbo.prompt_version_tags_seq'),
    prompt_version_id  INTEGER NOT NULL REFERENCES dbo.prompt_versions (id) ON DELETE CASCADE,
    tag_id             INTEGER NOT NULL REFERENCES dbo.tags (id) ON DELETE CASCADE,
    created_at         TIMESTAMP,
    updated_at         TIMESTAMP,
    UNIQUE (prompt_version_id, tag_id)
);

-- Índices
CREATE INDEX IF NOT EXISTS idx_tags_name ON dbo.tags (name);
CREATE INDEX IF NOT EXISTS idx_tags_slug ON dbo.tags (slug);
CREATE INDEX IF NOT EXISTS idx_prompt_version_tags_version_id ON dbo.prompt_version_tags (prompt_version_id);
CREATE INDEX IF NOT EXISTS idx_prompt_version_tags_tag_id ON dbo.prompt_version_tags (tag_id);

-- Inserir tags padrão baseadas na comunidade de prompts
INSERT INTO dbo.tags (name, slug, description, created_at, updated_at)
VALUES
    -- Categorias de Funcionalidade
    ('Code Generation', 'code-generation', 'Geração automática de código', NOW(), NOW()),
    ('Code Review', 'code-review', 'Revisão e análise de código', NOW(), NOW()),
    ('Refactoring', 'refactoring', 'Refatoração e melhoria de código', NOW(), NOW()),
    ('Bug Fixing', 'bug-fixing', 'Identificação e correção de bugs', NOW(), NOW()),
    ('Testing', 'testing', 'Geração e melhoria de testes', NOW(), NOW()),
    ('Unit Testing', 'unit-testing', 'Testes unitários', NOW(), NOW()),
    ('Integration Testing', 'integration-testing', 'Testes de integração', NOW(), NOW()),
    ('E2E Testing', 'e2e-testing', 'Testes end-to-end', NOW(), NOW()),
    ('Test Automation', 'test-automation', 'Automação de testes', NOW(), NOW()),
    ('TDD', 'tdd', 'Test-Driven Development', NOW(), NOW()),
    ('BDD', 'bdd', 'Behavior-Driven Development', NOW(), NOW()),
    ('Mocking', 'mocking', 'Criação de mocks e stubs', NOW(), NOW()),
    ('Documentation', 'documentation', 'Geração de documentação', NOW(), NOW()),
    ('Code Explanation', 'code-explanation', 'Explicação de código existente', NOW(), NOW()),
    ('Optimization', 'optimization', 'Otimização de performance', NOW(), NOW()),
    ('SDL', 'sdl', 'Prompts/Instruct para SDL', NOW(), NOW()),
    ('PDL', 'pdl', 'Prompts/Instruct para PDL', NOW(), NOW()),
    ('EDL', 'edl', 'Prompts/Instruct para EDL', NOW(), NOW()),

    -- Tipos de Desenvolvimento
    ('API Development', 'api-development', 'Desenvolvimento de APIs', NOW(), NOW()),
    ('Frontend', 'frontend', 'Desenvolvimento frontend', NOW(), NOW()),
    ('Backend', 'backend', 'Desenvolvimento backend', NOW(), NOW()),
    ('Database', 'database', 'Queries e modelagem de dados', NOW(), NOW()),
    ('DevOps', 'devops', 'Automação e infraestrutura', NOW(), NOW()),
    ('Mobile', 'mobile', 'Desenvolvimento mobile', NOW(), NOW()),
    
    -- Linguagens de Programação
    ('Java', 'java', 'Linguagem Java', NOW(), NOW()),
    ('Python', 'python', 'Linguagem Python', NOW(), NOW()),
    ('JavaScript', 'javascript', 'Linguagem JavaScript', NOW(), NOW()),
    ('TypeScript', 'typescript', 'Linguagem TypeScript', NOW(), NOW()),
    ('SQL', 'sql', 'Linguagem SQL', NOW(), NOW()),
    ('Go', 'go', 'Linguagem Go', NOW(), NOW()),
    ('Rust', 'rust', 'Linguagem Rust', NOW(), NOW()),
    ('C#', 'csharp', 'Linguagem C#', NOW(), NOW()),
    ('DELPHI', 'delphi', 'Linguagem Delphi', NOW(), NOW()),

    -- Frameworks e Tecnologias
    ('Spring Boot', 'spring-boot', 'Framework Spring Boot', NOW(), NOW()),
    ('React', 'react', 'Biblioteca React', NOW(), NOW()),
    ('Angular', 'angular', 'Framework Angular', NOW(), NOW()),
    ('Vue.js', 'vuejs', 'Framework Vue.js', NOW(), NOW()),
    ('Node.js', 'nodejs', 'Runtime Node.js', NOW(), NOW()),
    ('Django', 'django', 'Framework Django', NOW(), NOW()),
    ('Flask', 'flask', 'Framework Flask', NOW(), NOW()),
    ('Docker', 'docker', 'Containerização Docker', NOW(), NOW()),
    ('Kubernetes', 'kubernetes', 'Orquestração Kubernetes', NOW(), NOW()),
    ('JUnit', 'junit', 'Framework JUnit', NOW(), NOW()),
    ('Jest', 'jest', 'Framework Jest', NOW(), NOW()),
    ('Pytest', 'pytest', 'Framework Pytest', NOW(), NOW()),
    ('Selenium', 'selenium', 'Framework Selenium', NOW(), NOW()),
    ('Cypress', 'cypress', 'Framework Cypress', NOW(), NOW()),
    
    -- Padrões e Arquitetura
    ('Design Patterns', 'design-patterns', 'Padrões de projeto', NOW(), NOW()),
    ('Clean Code', 'clean-code', 'Código limpo e boas práticas', NOW(), NOW()),
    ('SOLID', 'solid', 'Princípios SOLID', NOW(), NOW()),
    ('Microservices', 'microservices', 'Arquitetura de microsserviços', NOW(), NOW()),
    ('REST API', 'rest-api', 'APIs RESTful', NOW(), NOW()),
    ('GraphQL', 'graphql', 'APIs GraphQL', NOW(), NOW()),

    -- Segurança
    ('Security', 'security', 'Segurança da informação', NOW(), NOW()),
    ('Authentication', 'authentication', 'Autenticação de usuários', NOW(), NOW()),
    ('Authorization', 'authorization', 'Autorização e permissões', NOW(), NOW()),
    ('Encryption', 'encryption', 'Criptografia', NOW(), NOW()),
    
    -- Data Science e AI
    ('Machine Learning', 'machine-learning', 'Aprendizado de máquina', NOW(), NOW()),
    ('Data Analysis', 'data-analysis', 'Análise de dados', NOW(), NOW()),
    ('AI Assistant', 'ai-assistant', 'Assistentes de IA', NOW(), NOW()),
    ('Natural Language', 'natural-language', 'Processamento de linguagem natural', NOW(), NOW()),
    
    -- Utilidades
    ('Automation', 'automation', 'Automação de tarefas', NOW(), NOW()),
    ('CLI Tools', 'cli-tools', 'Ferramentas de linha de comando', NOW(), NOW()),
    ('Debugging', 'debugging', 'Depuração de código', NOW(), NOW()),
    ('Performance', 'performance', 'Análise de performance', NOW(), NOW()),
    ('Migration', 'migration', 'Migração de código ou dados', NOW(), NOW()),
    
    -- Níveis de Complexidade
    ('Beginner', 'beginner', 'Nível iniciante', NOW(), NOW()),
    ('Intermediate', 'intermediate', 'Nível intermediário', NOW(), NOW()),
    ('Advanced', 'advanced', 'Nível avançado', NOW(), NOW()),
    
    -- Status e Uso
    ('Production Ready', 'production-ready', 'Pronto para produção', NOW(), NOW()),
    ('Experimental', 'experimental', 'Experimental', NOW(), NOW()),
    ('Template', 'template', 'Template reutilizável', NOW(), NOW()),
    ('Snippet', 'snippet', 'Trecho de código', NOW(), NOW());
