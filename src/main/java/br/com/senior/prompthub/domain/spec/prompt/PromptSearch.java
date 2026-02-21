package br.com.senior.prompthub.domain.spec.prompt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromptSearch {
    private String name;
    private Long teamId;
    private Long ownerId;
}
