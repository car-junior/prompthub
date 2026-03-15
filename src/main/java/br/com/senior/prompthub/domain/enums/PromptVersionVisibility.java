package br.com.senior.prompthub.domain.enums;

import lombok.Getter;

@Getter
public enum PromptVersionVisibility {
    PRIVATE("Privado"),
    INTERNAL("Interno"),
    PUBLIC("Público");

    private final String description;

    PromptVersionVisibility(String description) {
        this.description = description;
    }

}
