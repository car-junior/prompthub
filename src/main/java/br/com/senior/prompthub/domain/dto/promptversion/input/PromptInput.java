package br.com.senior.prompthub.domain.dto.promptversion.input;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromptInput {
    @NotNull
    private Long id;
}
