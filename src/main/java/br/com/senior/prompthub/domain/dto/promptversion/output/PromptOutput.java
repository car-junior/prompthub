package br.com.senior.prompthub.domain.dto.promptversion.output;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromptOutput {
    private Long id;
    private String name;
    private TeamOutput team;
    private UserOutput owner;
}
