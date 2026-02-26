package br.com.senior.prompthub.domain.spec.prompt;

import br.com.senior.prompthub.domain.spec.UserContextAware;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromptSearch implements UserContextAware {
    private String query;
    private Long userId;
    private List<Long> teamsIds;

    @Override
    public void setCurrentUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public void setAccessibleTeamIds(List<Long> teamsIds) {
        this.teamsIds = teamsIds;
    }
}
