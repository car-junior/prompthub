-- Sequences
CREATE SEQUENCE IF NOT EXISTS dbo.prompts_seq;
CREATE SEQUENCE IF NOT EXISTS dbo.prompt_versions_seq;
CREATE SEQUENCE IF NOT EXISTS dbo.prompt_history_seq;
CREATE SEQUENCE IF NOT EXISTS dbo.prompt_dependencies_seq;

-- Tabela principal de prompts (pode ser de equipe ou pessoal)
CREATE TABLE IF NOT EXISTS dbo.prompts
(
    id          INTEGER PRIMARY KEY DEFAULT nextval('dbo.prompts_seq'),
    team_id     INTEGER REFERENCES dbo.teams (id),    -- NULL = prompt pessoal
    owner_id    INTEGER REFERENCES dbo.users (id),    -- Dono do prompt (obrigatório se team_id for NULL)
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    created_at  TIMESTAMP,
    updated_at  TIMESTAMP,
    CHECK (
        (team_id IS NOT NULL AND owner_id IS NULL) OR -- Prompt de equipe
        (team_id IS NULL AND owner_id IS NOT NULL)    -- Prompt pessoal
        ),
    UNIQUE (team_id, name),                           -- Nome único dentro da equipe
    UNIQUE (owner_id, name)                           -- Nome único para cada usuário
);


-- Tabela de versões dos prompts

-- status das versões:
-- 'DRAFT',       -- Rascunho/em desenvolvimento
-- 'ACTIVE',      -- Ativa e disponível para uso em produção
-- 'INACTIVE',    -- Desativada temporariamente (pode ser reativada)
-- 'DEPRECATED',  -- Marcada como obsoleta (não recomendada, mas ainda funcional)
-- 'DELETED'      -- Deletada logicamente (não pode ser reativada)

-- visibility das versões:
-- 'PRIVATE', -- Apenas membros do time
-- 'INTERNAL', -- Todos usuários autenticados
-- 'PUBLIC' -- Qualquer pessoa com o link
CREATE TABLE IF NOT EXISTS dbo.prompt_versions
(
    id           INTEGER PRIMARY KEY  DEFAULT nextval('dbo.prompt_versions_seq'),
    prompt_id    INTEGER     NOT NULL REFERENCES dbo.prompts (id),
    version      VARCHAR(50) NOT NULL,
    content      TEXT        NOT NULL,
    status       VARCHAR(30) NOT NULL DEFAULT 'DRAFT',
    visibility   VARCHAR(30) NOT NULL DEFAULT 'PRIVATE',
    change_notes TEXT,
    author_id    INTEGER REFERENCES dbo.users (id),
    created_at   TIMESTAMP,
    updated_at   TIMESTAMP,
    UNIQUE (prompt_id, version)
);

-- Tabela de histórico de mudanças de status
CREATE TABLE IF NOT EXISTS dbo.prompt_history
(
    id                INTEGER PRIMARY KEY DEFAULT nextval('dbo.prompt_history_seq'),
    prompt_version_id INTEGER     NOT NULL REFERENCES dbo.prompt_versions (id),
    old_status        VARCHAR(30),
    new_status        VARCHAR(30) NOT NULL,
    author_id         INTEGER REFERENCES dbo.users (id),
    created_at        TIMESTAMP,
    updated_at        TIMESTAMP,
    notes             TEXT
);

-- Tabela de dependências entre prompts
CREATE TABLE IF NOT EXISTS dbo.prompt_dependencies
(
    id                   INTEGER PRIMARY KEY DEFAULT nextval('dbo.prompt_dependencies_seq'),
    prompt_id            INTEGER NOT NULL REFERENCES dbo.prompts (id),
    depends_on_prompt_id INTEGER NOT NULL REFERENCES dbo.prompts (id),
    min_version          VARCHAR(50),
    created_at           TIMESTAMP,
    updated_at           TIMESTAMP,
    UNIQUE (prompt_id, depends_on_prompt_id)
);

-- Índices
CREATE INDEX IF NOT EXISTS idx_prompts_team_id ON dbo.prompts (team_id);
CREATE INDEX IF NOT EXISTS idx_prompts_owner_id ON dbo.prompts (owner_id);
CREATE INDEX IF NOT EXISTS idx_prompts_name ON dbo.prompts (name);
CREATE INDEX IF NOT EXISTS idx_prompt_versions_prompt_id ON dbo.prompt_versions (prompt_id);
CREATE INDEX IF NOT EXISTS idx_prompt_versions_status ON dbo.prompt_versions (status);
CREATE INDEX IF NOT EXISTS idx_prompt_versions_visibility ON dbo.prompt_versions (visibility);
CREATE INDEX IF NOT EXISTS idx_prompt_versions_active ON dbo.prompt_versions (status) WHERE status = 'ACTIVE';
CREATE INDEX IF NOT EXISTS idx_prompt_versions_created_by ON dbo.prompt_versions (author_id);
CREATE INDEX IF NOT EXISTS idx_prompt_history_version_id ON dbo.prompt_history (prompt_version_id);
CREATE INDEX IF NOT EXISTS idx_prompt_history_performed_at ON dbo.prompt_history (author_id DESC);
CREATE INDEX IF NOT EXISTS idx_prompt_history_updated_by ON dbo.prompt_history (author_id);
