package br.com.senior.prompthub.domain.dto.team;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamInput {
    @NotBlank
    private String name;

    private String description;
}
