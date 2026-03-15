package br.com.senior.prompthub.domain.dto.promptversion.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromptVersionInput {
    @Valid
    @NotNull
    private PromptInput prompt;

    @NotBlank
    private String content;

    @NotBlank
    private String changeNotes;
}
