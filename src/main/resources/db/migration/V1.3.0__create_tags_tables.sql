-- Sequences
CREATE SEQUENCE IF NOT EXISTS dbo.tags_seq;
CREATE SEQUENCE IF NOT EXISTS dbo.prompt_tags_seq;

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

-- Tabela de relacionamento prompt-tag (many-to-many)
CREATE TABLE IF NOT EXISTS dbo.prompt_tags
(
    id         INTEGER PRIMARY KEY DEFAULT nextval('dbo.prompt_tags_seq'),
    prompt_id  INTEGER NOT NULL REFERENCES dbo.prompts (id) ON DELETE CASCADE,
    tag_id     INTEGER NOT NULL REFERENCES dbo.tags (id) ON DELETE CASCADE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    UNIQUE (prompt_id, tag_id)
);

-- Índices
CREATE INDEX IF NOT EXISTS idx_tags_name ON dbo.tags (name);
CREATE INDEX IF NOT EXISTS idx_tags_slug ON dbo.tags (slug);
CREATE INDEX IF NOT EXISTS idx_prompt_tags_prompt_id ON dbo.prompt_tags (prompt_id);
CREATE INDEX IF NOT EXISTS idx_prompt_tags_tag_id ON dbo.prompt_tags (tag_id);
