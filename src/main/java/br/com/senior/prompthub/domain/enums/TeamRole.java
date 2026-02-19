package br.com.senior.prompthub.domain.enums;

import lombok.Getter;

@Getter
public enum TeamRole {
    TEAM_OWNER("Dono do Time"),     // Dono do time (gerencia membros e pode deletar)
    DEV("Desenvolvedor"),           // Desenvolvedor (cria/edita prompts)
    VIEWER("Visualizador");         // Apenas visualização

    private final String description;

    TeamRole(String description) {
        this.description = description;
    }
}
