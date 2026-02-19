-- Sequences
CREATE SEQUENCE IF NOT EXISTS dbo.teams_seq;
CREATE SEQUENCE IF NOT EXISTS dbo.users_seq;
CREATE SEQUENCE IF NOT EXISTS dbo.team_users_seq;

-- Tabela de equipes
CREATE TABLE IF NOT EXISTS dbo.teams
(
    id          INTEGER PRIMARY KEY   DEFAULT nextval('dbo.teams_seq'),
    name        VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255),
    status      VARCHAR(30)  NOT NULL DEFAULT 'ACTIVE',
    created_at  TIMESTAMP,
    updated_at  TIMESTAMP
);

-- Tabela de usuários
-- role:
-- 'ADMIN',   -- Administrador do Sistema (acesso total, gerencia equipes e usuários)
-- 'USER',    -- Usuário
CREATE TABLE IF NOT EXISTS dbo.users
(
    id                   INTEGER PRIMARY KEY   DEFAULT nextval('dbo.users_seq'),
    username             VARCHAR(100) NOT NULL UNIQUE,
    password             VARCHAR(255),
    email                VARCHAR(255) NOT NULL UNIQUE,
    role                 VARCHAR(30)  NOT NULL DEFAULT 'USER',
    status               VARCHAR(30)  NOT NULL DEFAULT 'ACTIVE',
    must_change_password BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at           TIMESTAMP,
    updated_at           TIMESTAMP
);

-- Tabela de relacionamento usuário-equipe (um usuário pode estar em várias equipes)
-- role:
-- 'TEAM_OWNER',   -- Dono do time (gerencia membros e pode deletar)
-- 'DEV',          -- Desenvolvedor (cria/edita prompts)
-- 'VIEWER'        -- Apenas visualização
CREATE TABLE IF NOT EXISTS dbo.team_users
(
    id         INTEGER PRIMARY KEY  DEFAULT nextval('dbo.team_users_seq'),
    team_id    INTEGER     NOT NULL REFERENCES dbo.teams (id),
    user_id    INTEGER     NOT NULL REFERENCES dbo.users (id),
    role       VARCHAR(30) NOT NULL DEFAULT 'VIEWER',
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    UNIQUE (team_id, user_id)
);

-- Índices
CREATE INDEX IF NOT EXISTS idx_teams_status ON dbo.teams (status);
CREATE INDEX IF NOT EXISTS idx_teams_active ON dbo.teams (status) WHERE status = 'ACTIVE';
CREATE INDEX IF NOT EXISTS idx_users_username ON dbo.users (username);
CREATE INDEX IF NOT EXISTS idx_users_global_role ON dbo.users (role);
CREATE INDEX IF NOT EXISTS idx_users_status ON dbo.users (status);
CREATE INDEX IF NOT EXISTS idx_users_active ON dbo.users (status) WHERE status = 'ACTIVE';
CREATE INDEX IF NOT EXISTS idx_team_users_team_id ON dbo.team_users (team_id);
CREATE INDEX IF NOT EXISTS idx_team_users_user_id ON dbo.team_users (user_id);
CREATE INDEX IF NOT EXISTS idx_team_users_team_role ON dbo.team_users (role);
