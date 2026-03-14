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
