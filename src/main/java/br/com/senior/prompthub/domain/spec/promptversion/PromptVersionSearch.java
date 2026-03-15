package br.com.senior.prompthub.domain.spec.promptversion;

import br.com.senior.prompthub.domain.enums.PromptVersionStatus;
import br.com.senior.prompthub.domain.enums.PromptVersionVisibility;
import br.com.senior.prompthub.domain.spec.UserContextAware;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromptVersionSearch implements UserContextAware {
    private Long promptId;
    private String query;
    private PromptVersionStatus status;
    private PromptVersionVisibility visibility;
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
