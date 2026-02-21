package br.com.senior.prompthub.domain.enums;

import lombok.Getter;

@Getter
public enum GlobalRole {
    ADMIN("Administrador do Sistema"),
    USER("Usuário");

    private final String description;

    GlobalRole(String description) {
        this.description = description;
    }

    public String getRoleAuthority() {
        return "ROLE_" + this.name();
    }
}
