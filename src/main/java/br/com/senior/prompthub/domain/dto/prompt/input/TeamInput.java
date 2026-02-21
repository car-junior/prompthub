package br.com.senior.prompthub.domain.dto.prompt.input;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamInput {
    @NotNull
    private Long id;
}