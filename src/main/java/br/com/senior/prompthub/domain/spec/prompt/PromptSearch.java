package br.com.senior.prompthub.domain.spec.prompt;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromptSearch {
    private String query;
    private Long ownerId;
    private List<Long> teamsId;
}
