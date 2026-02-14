package br.com.senior.prompthub.domain.enums;

public enum UserRole {
    ADMIN,        // Acesso total ao sistema
    TEAM_OWNER,   // Dono do time (gerencia membros e pode deletar)
    DEV,          // Desenvolvedor (cria/edita prompts)
    VIEWER        // Apenas visualização
}
