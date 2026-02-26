-- ============================================
-- DADOS DE EXEMPLO PARA TESTES
-- ============================================
-- Senha para todos: 123456
-- IMPORTANTE: Este arquivo é executado toda vez que a aplicação inicia
-- Para evitar duplicação, limpe o banco ou use Flyway migration

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
    (nextval('dbo.users_seq'), 'carlos.lima', '$2a$10$HUcHPkU5tewI94gQHnGDzuAB2vcbydUFjLOde1FWMOtmtdYr1ZSqO', 'carlos.lima@email.com', 'USER', 'ACTIVE', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ============================================
-- 2. TIMES
-- ============================================
INSERT INTO dbo.teams (id, name, description, status, created_at, updated_at)
VALUES
    (nextval('dbo.teams_seq'), 'Time Backend', 'Equipe responsável pelo desenvolvimento backend', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.teams_seq'), 'Time Frontend', 'Equipe responsável pelo desenvolvimento frontend', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.teams_seq'), 'Time DevOps', 'Equipe responsável por infraestrutura e CI/CD', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.teams_seq'), 'Time QA', 'Equipe de qualidade e testes', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ============================================
-- 3. RELACIONAMENTO USUÁRIOS <-> TIMES
-- ============================================
INSERT INTO dbo.team_users(id, team_id, user_id, role, created_at, updated_at)
VALUES
    -- Time Backend
    (nextval('dbo.team_users_seq'), 1, (SELECT id FROM dbo.users WHERE username = 'joao.silva'), 'TEAM_OWNER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.team_users_seq'), 1, (SELECT id FROM dbo.users WHERE username = 'maria.santos'), 'DEV', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.team_users_seq'), 1, (SELECT id FROM dbo.users WHERE username = 'pedro.costa'), 'VIEWER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Time Frontend
    (nextval('dbo.team_users_seq'), 2, (SELECT id FROM dbo.users WHERE username = 'ana.oliveira'), 'TEAM_OWNER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.team_users_seq'), 2, (SELECT id FROM dbo.users WHERE username = 'maria.santos'), 'DEV', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.team_users_seq'), 2, (SELECT id FROM dbo.users WHERE username = 'carlos.lima'), 'DEV', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Time DevOps
    (nextval('dbo.team_users_seq'), 3, (SELECT id FROM dbo.users WHERE username = 'carlos.lima'), 'TEAM_OWNER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.team_users_seq'), 3, (SELECT id FROM dbo.users WHERE username = 'pedro.costa'), 'DEV', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Time QA
    (nextval('dbo.team_users_seq'), 4, (SELECT id FROM dbo.users WHERE username = 'ana.oliveira'), 'TEAM_OWNER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.team_users_seq'), 4, (SELECT id FROM dbo.users WHERE username = 'joao.silva'), 'VIEWER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ============================================
-- 4. PROMPTS DE TIMES
-- ============================================
INSERT INTO dbo.prompts (id, team_id, owner_id, name, description, created_at, updated_at)
VALUES
    -- Time Backend
    (nextval('dbo.prompts_seq'), 1, NULL, 'Análise de Código Java', 'Prompt para revisar código Java e sugerir melhorias', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompts_seq'), 1, NULL, 'Geração de Testes Unitários', 'Prompt para gerar testes unitários automaticamente', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompts_seq'), 1, NULL, 'Documentação de API', 'Prompt para gerar documentação de endpoints REST', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Time Frontend
    (nextval('dbo.prompts_seq'), 2, NULL, 'Componente React', 'Prompt para criar componentes React com TypeScript', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompts_seq'), 2, NULL, 'Validação de Formulário', 'Prompt para criar validações de formulário', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Time DevOps
    (nextval('dbo.prompts_seq'), 3, NULL, 'Pipeline CI/CD', 'Prompt para criar pipelines de CI/CD', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompts_seq'), 3, NULL, 'Dockerfile Otimizado', 'Prompt para criar Dockerfiles otimizados', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    -- Time QA
    (nextval('dbo.prompts_seq'), 4, NULL, 'Casos de Teste', 'Prompt para gerar casos de teste', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (nextval('dbo.prompts_seq'), 4, NULL, 'Testes E2E', 'Prompt para criar testes end-to-end', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

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
    (nextval('dbo.prompts_seq'), NULL, (SELECT id FROM dbo.users WHERE username = 'carlos.lima'), 'Kubernetes Helper', 'Prompt para ajudar com configurações Kubernetes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
