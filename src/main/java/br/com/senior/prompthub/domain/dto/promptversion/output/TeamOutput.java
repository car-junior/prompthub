package br.com.senior.prompthub.domain.dto.promptversion.output;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamOutput {
    private Long id;
    private String name;
}