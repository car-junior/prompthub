package br.com.senior.prompthub.domain.enums;

import lombok.Getter;

@Getter
public enum EntityStatus {
    ACTIVE("Ativo"),
    INACTIVE("Inativo"),
    DELETED("Excluído");

    private final String description;

    EntityStatus(String description) {
        this.description = description;
    }

}
