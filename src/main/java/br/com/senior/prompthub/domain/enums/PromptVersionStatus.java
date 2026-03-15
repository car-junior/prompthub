package br.com.senior.prompthub.domain.enums;

import lombok.Getter;

@Getter
public enum PromptVersionStatus {
    DRAFT("Desenvolvimento"),
    ACTIVE("Ativo"),
    INACTIVE("Inativo"),
    DEPRECATED("Deprecado"),
    DELETED("Deletado");

    private final String description;

    PromptVersionStatus(String description) {
        this.description = description;
    }

}
