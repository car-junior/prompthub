-- ============================================
-- DADOS DE EXEMPLO PARA TESTES
-- ============================================
-- Senha para todos: 123456

-- ============================================
-- 1. USUÁRIOS
-- ============================================
INSERT INTO dbo.users (id, username, password, email, role, status, must_change_password, created_at, updated_at)
VALUES
    (nextval('dbo.users_seq'), 'admin', '$2a$10$HUcHPkU5tewI94gQHnGDzuAB2vcbydUFjLOde1FWMOtmtdYr1ZSqO', 'admin@prompthub.com', 'ADMIN', 'ACTIVE', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.users_seq'), 'joao.silva', '$2a$10$HUcHPkU5tewI94gQHnGDzuAB2vcbydUFjLOde1FWMOtmtdYr1ZSqO', 'joao.silva@email.com', 'USER', 'ACTIVE', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.users_seq'), 'maria.santos', '$2a$10$HUcHPkU5tewI94gQHnGDzuAB2vcbydUFjLOde1FWMOtmtdYr1ZSqO', 'maria.santos@email.com', 'USER', 'ACTIVE', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.users_seq'), 'pedro.costa', '$2a$10$HUcHPkU5tewI94gQHnGDzuAB2vcbydUFjLOde1FWMOtmtdYr1ZSqO', 'pedro.costa@email.com', 'USER', 'ACTIVE', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.users_seq'), 'julia.oliveira', '$2a$10$HUcHPkU5tewI94gQHnGDzuAB2vcbydUFjLOde1FWMOtmtdYr1ZSqO', 'ana.oliveira@email.com', 'USER', 'ACTIVE', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.users_seq'), 'carlos.lima', '$2a$10$HUcHPkU5tewI94gQHnGDzuAB2vcbydUFjLOde1FWMOtmtdYr1ZSqO', 'carlos.lima@email.com', 'USER', 'ACTIVE', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;

-- ============================================
-- 2. TIMES
-- ============================================
INSERT INTO dbo.teams (id, name, description, status, created_at, updated_at)
VALUES
    (nextval('dbo.teams_seq'), 'Time Backend', 'Equipe responsável pelo desenvolvimento backend', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.teams_seq'), 'Time Frontend', 'Equipe responsável pelo desenvolvimento frontend', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.teams_seq'), 'Time DevOps', 'Equipe responsável por infraestrutura e CI/CD', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.teams_seq'), 'Time QA', 'Equipe de qualidade e testes', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;

-- ============================================
-- 3. RELACIONAMENTO USUÁRIOS <-> TIMES
-- ============================================
INSERT INTO dbo.team_users(id, team_id, user_id, role, created_at, updated_at)
VALUES
    -- Time Backend
    (nextval('dbo.team_users_seq'), (SELECT id FROM dbo.teams WHERE name = 'Time Backend'), (SELECT id FROM dbo.users WHERE username = 'joao.silva'), 'TEAM_OWNER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.team_users_seq'), (SELECT id FROM dbo.teams WHERE name = 'Time Backend'), (SELECT id FROM dbo.users WHERE username = 'maria.santos'), 'DEV', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.team_users_seq'), (SELECT id FROM dbo.teams WHERE name = 'Time Backend'), (SELECT id FROM dbo.users WHERE username = 'pedro.costa'), 'VIEWER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Time Frontend
    (nextval('dbo.team_users_seq'), (SELECT id FROM dbo.teams WHERE name = 'Time Frontend'), (SELECT id FROM dbo.users WHERE username = 'julia.oliveira'), 'TEAM_OWNER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.team_users_seq'), (SELECT id FROM dbo.teams WHERE name = 'Time Frontend'), (SELECT id FROM dbo.users WHERE username = 'maria.santos'), 'DEV', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.team_users_seq'), (SELECT id FROM dbo.teams WHERE name = 'Time Frontend'), (SELECT id FROM dbo.users WHERE username = 'carlos.lima'), 'DEV', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Time DevOps
    (nextval('dbo.team_users_seq'), (SELECT id FROM dbo.teams WHERE name = 'Time DevOps'), (SELECT id FROM dbo.users WHERE username = 'carlos.lima'), 'TEAM_OWNER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.team_users_seq'), (SELECT id FROM dbo.teams WHERE name = 'Time DevOps'), (SELECT id FROM dbo.users WHERE username = 'pedro.costa'), 'DEV', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Time QA
    (nextval('dbo.team_users_seq'), (SELECT id FROM dbo.teams WHERE name = 'Time QA'), (SELECT id FROM dbo.users WHERE username = 'julia.oliveira'), 'TEAM_OWNER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.team_users_seq'), (SELECT id FROM dbo.teams WHERE name = 'Time QA'), (SELECT id FROM dbo.users WHERE username = 'joao.silva'), 'VIEWER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;

-- ============================================
-- 4. PROMPTS DE TIMES
-- ============================================
INSERT INTO dbo.prompts (id, team_id, owner_id, name, description, created_at, updated_at)
VALUES
    -- Time Backend
    (nextval('dbo.prompts_seq'), (SELECT id FROM dbo.teams WHERE name = 'Time Backend'), NULL, 'Análise de Código Java', 'Prompt para revisar código Java e sugerir melhorias', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompts_seq'), (SELECT id FROM dbo.teams WHERE name = 'Time Backend'), NULL, 'Geração de Testes Unitários', 'Prompt para gerar testes unitários automaticamente', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompts_seq'), (SELECT id FROM dbo.teams WHERE name = 'Time Backend'), NULL, 'Documentação de API', 'Prompt para gerar documentação de endpoints REST', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Time Frontend
    (nextval('dbo.prompts_seq'), (SELECT id FROM dbo.teams WHERE name = 'Time Frontend'), NULL, 'Componente React', 'Prompt para criar componentes React com TypeScript', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompts_seq'), (SELECT id FROM dbo.teams WHERE name = 'Time Frontend'), NULL, 'Validação de Formulário', 'Prompt para criar validações de formulário', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Time DevOps
    (nextval('dbo.prompts_seq'), (SELECT id FROM dbo.teams WHERE name = 'Time DevOps'), NULL, 'Pipeline CI/CD', 'Prompt para criar pipelines de CI/CD', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompts_seq'), (SELECT id FROM dbo.teams WHERE name = 'Time DevOps'), NULL, 'Dockerfile Otimizado', 'Prompt para criar Dockerfiles otimizados', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Time QA
    (nextval('dbo.prompts_seq'), (SELECT id FROM dbo.teams WHERE name = 'Time QA'), NULL, 'Casos de Teste', 'Prompt para gerar casos de teste', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompts_seq'), (SELECT id FROM dbo.teams WHERE name = 'Time QA'), NULL, 'Testes E2E', 'Prompt para criar testes end-to-end', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;

-- ============================================
-- 5. PROMPTS PESSOAIS
-- ============================================
INSERT INTO dbo.prompts (id, team_id, owner_id, name, description, created_at, updated_at)
VALUES
    (nextval('dbo.prompts_seq'), NULL, (SELECT id FROM dbo.users WHERE username = 'joao.silva'), 'Meu Prompt de Estudo', 'Prompt pessoal para estudos de algoritmos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompts_seq'), NULL, (SELECT id FROM dbo.users WHERE username = 'joao.silva'), 'Refatoração Pessoal', 'Prompt para refatorar meus códigos pessoais', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompts_seq'), NULL, (SELECT id FROM dbo.users WHERE username = 'maria.santos'), 'Aprendizado de Design Patterns', 'Prompt para estudar padrões de projeto', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompts_seq'), NULL, (SELECT id FROM dbo.users WHERE username = 'pedro.costa'), 'Otimização de Queries', 'Prompt para otimizar queries SQL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompts_seq'), NULL, (SELECT id FROM dbo.users WHERE username = 'julia.oliveira'), 'Code Review Pessoal', 'Prompt para revisar meus próprios códigos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompts_seq'), NULL, (SELECT id FROM dbo.users WHERE username = 'carlos.lima'), 'Kubernetes Helper', 'Prompt para ajudar com configurações Kubernetes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;

-- ============================================
-- 6. PROMPT VERSIONS
-- ============================================
INSERT INTO dbo.prompt_versions (id, prompt_id, author_id, version, content, change_notes, created_at, updated_at)
VALUES
    -- Análise de Código Java - 3 versões
    (nextval('dbo.prompt_versions_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Análise de Código Java'), (SELECT id FROM dbo.users WHERE username = 'joao.silva'), 'V1', 'Analise o código Java a seguir e aponte problemas de qualidade.', 'Versão inicial', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompt_versions_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Análise de Código Java'), (SELECT id FROM dbo.users WHERE username = 'maria.santos'), 'V2', 'Analise o código Java a seguir. Aponte problemas de qualidade, performance e segurança.', 'Adicionado foco em performance e segurança', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompt_versions_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Análise de Código Java'), (SELECT id FROM dbo.users WHERE username = 'joao.silva'), 'V3', 'Analise o código Java a seguir. Aponte problemas de qualidade, performance e segurança. Sugira refatorações com exemplos práticos.', 'Adicionado sugestão de refatorações com exemplos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Geração de Testes Unitários - 2 versões
    (nextval('dbo.prompt_versions_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Geração de Testes Unitários'), (SELECT id FROM dbo.users WHERE username = 'maria.santos'), 'V1', 'Gere testes unitários JUnit 5 para o método a seguir.', 'Versão inicial', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompt_versions_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Geração de Testes Unitários'), (SELECT id FROM dbo.users WHERE username = 'maria.santos'), 'V2', 'Gere testes unitários JUnit 5 com Mockito para o método a seguir. Cubra casos de sucesso, falha e edge cases.', 'Adicionado Mockito e cobertura de edge cases', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Documentação de API - 1 versão
    (nextval('dbo.prompt_versions_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Documentação de API'), (SELECT id FROM dbo.users WHERE username = 'joao.silva'), 'V1', 'Gere documentação OpenAPI 3.0 para o endpoint REST a seguir, incluindo descrições, parâmetros e exemplos de resposta.', 'Versão inicial', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Componente React - 2 versões
    (nextval('dbo.prompt_versions_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Componente React'), (SELECT id FROM dbo.users WHERE username = 'maria.santos'), 'V1', 'Crie um componente React funcional com TypeScript para o seguinte requisito.', 'Versão inicial', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompt_versions_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Componente React'), (SELECT id FROM dbo.users WHERE username = 'carlos.lima'), 'V2', 'Crie um componente React funcional com TypeScript. Utilize hooks adequados, trate estados de loading e erro, e adicione acessibilidade básica.', 'Adicionado tratamento de estados e acessibilidade', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Pipeline CI/CD - 2 versões
    (nextval('dbo.prompt_versions_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Pipeline CI/CD'), (SELECT id FROM dbo.users WHERE username = 'carlos.lima'), 'V1', 'Crie um pipeline CI/CD para o projeto a seguir.', 'Versão inicial', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompt_versions_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Pipeline CI/CD'), (SELECT id FROM dbo.users WHERE username = 'carlos.lima'), 'V2', 'Crie um pipeline CI/CD completo com stages de build, test, security scan e deploy para o projeto a seguir. Inclua rollback automático em caso de falha.', 'Adicionado security scan e rollback automático', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Meu Prompt de Estudo - 1 versão
    (nextval('dbo.prompt_versions_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Meu Prompt de Estudo'), (SELECT id FROM dbo.users WHERE username = 'joao.silva'), 'V1', 'Explique o algoritmo a seguir passo a passo, com complexidade de tempo e espaço, e sugira otimizações.', 'Versão inicial', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Otimização de Queries - 2 versões
    (nextval('dbo.prompt_versions_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Otimização de Queries'), (SELECT id FROM dbo.users WHERE username = 'pedro.costa'), 'V1', 'Analise a query SQL a seguir e sugira otimizações.', 'Versão inicial', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompt_versions_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Otimização de Queries'), (SELECT id FROM dbo.users WHERE username = 'pedro.costa'), 'V2', 'Analise a query SQL a seguir. Identifique gargalos, sugira índices, reescreva se necessário e explique o impacto de cada mudança.', 'Análise mais detalhada com sugestão de índices', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;

-- ============================================
-- 7. TAGS
-- ============================================
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
    ('Snippet', 'snippet', 'Trecho de código', NOW(), NOW())
ON CONFLICT DO NOTHING;

-- ============================================
-- 8. PROMPT TAGS
-- ============================================
INSERT INTO dbo.prompt_tags (id, prompt_id, tag_id, created_at, updated_at)
VALUES
    -- Análise de Código Java -> Java, Code Review, Spring Boot
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Análise de Código Java'), (SELECT id FROM dbo.tags WHERE slug = 'java'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Análise de Código Java'), (SELECT id FROM dbo.tags WHERE slug = 'code-review'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Análise de Código Java'), (SELECT id FROM dbo.tags WHERE slug = 'backend'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Geração de Testes Unitários -> Testing, Unit Testing, Java, JUnit
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Geração de Testes Unitários'), (SELECT id FROM dbo.tags WHERE slug = 'unit-testing'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Geração de Testes Unitários'), (SELECT id FROM dbo.tags WHERE slug = 'java'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Geração de Testes Unitários'), (SELECT id FROM dbo.tags WHERE slug = 'junit'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Documentação de API -> Documentation, REST API, API Development
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Documentação de API'), (SELECT id FROM dbo.tags WHERE slug = 'documentation'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Documentação de API'), (SELECT id FROM dbo.tags WHERE slug = 'rest-api'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Documentação de API'), (SELECT id FROM dbo.tags WHERE slug = 'api-development'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Componente React -> React, Frontend, TypeScript
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Componente React'), (SELECT id FROM dbo.tags WHERE slug = 'react'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Componente React'), (SELECT id FROM dbo.tags WHERE slug = 'frontend'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Componente React'), (SELECT id FROM dbo.tags WHERE slug = 'typescript'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Validação de Formulário -> Frontend, JavaScript
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Validação de Formulário'), (SELECT id FROM dbo.tags WHERE slug = 'frontend'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Validação de Formulário'), (SELECT id FROM dbo.tags WHERE slug = 'javascript'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Pipeline CI/CD -> DevOps, Docker, Automation
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Pipeline CI/CD'), (SELECT id FROM dbo.tags WHERE slug = 'devops'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Pipeline CI/CD'), (SELECT id FROM dbo.tags WHERE slug = 'docker'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Pipeline CI/CD'), (SELECT id FROM dbo.tags WHERE slug = 'automation'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Dockerfile Otimizado -> Docker, DevOps
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Dockerfile Otimizado'), (SELECT id FROM dbo.tags WHERE slug = 'docker'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Dockerfile Otimizado'), (SELECT id FROM dbo.tags WHERE slug = 'devops'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Casos de Teste -> Testing, TDD
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Casos de Teste'), (SELECT id FROM dbo.tags WHERE slug = 'testing'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Casos de Teste'), (SELECT id FROM dbo.tags WHERE slug = 'tdd'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Testes E2E -> E2E Testing, Selenium, Cypress
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Testes E2E'), (SELECT id FROM dbo.tags WHERE slug = 'e2e-testing'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Testes E2E'), (SELECT id FROM dbo.tags WHERE slug = 'cypress'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Meu Prompt de Estudo -> Beginner, Code Explanation
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Meu Prompt de Estudo'), (SELECT id FROM dbo.tags WHERE slug = 'code-explanation'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Meu Prompt de Estudo'), (SELECT id FROM dbo.tags WHERE slug = 'beginner'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Refatoração Pessoal -> Refactoring, Clean Code
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Refatoração Pessoal'), (SELECT id FROM dbo.tags WHERE slug = 'refactoring'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Refatoração Pessoal'), (SELECT id FROM dbo.tags WHERE slug = 'clean-code'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Aprendizado de Design Patterns -> Design Patterns, SOLID
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Aprendizado de Design Patterns'), (SELECT id FROM dbo.tags WHERE slug = 'design-patterns'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Aprendizado de Design Patterns'), (SELECT id FROM dbo.tags WHERE slug = 'solid'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Otimização de Queries -> SQL, Database, Performance
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Otimização de Queries'), (SELECT id FROM dbo.tags WHERE slug = 'sql'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Otimização de Queries'), (SELECT id FROM dbo.tags WHERE slug = 'database'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Otimização de Queries'), (SELECT id FROM dbo.tags WHERE slug = 'performance'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Code Review Pessoal -> Code Review, Clean Code
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Code Review Pessoal'), (SELECT id FROM dbo.tags WHERE slug = 'code-review'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Code Review Pessoal'), (SELECT id FROM dbo.tags WHERE slug = 'clean-code'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Kubernetes Helper -> Kubernetes, DevOps
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Kubernetes Helper'), (SELECT id FROM dbo.tags WHERE slug = 'kubernetes'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompt_tags_seq'), (SELECT id FROM dbo.prompts WHERE name = 'Kubernetes Helper'), (SELECT id FROM dbo.tags WHERE slug = 'devops'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)

ON CONFLICT DO NOTHING;