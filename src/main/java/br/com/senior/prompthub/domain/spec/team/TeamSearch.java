package br.com.senior.prompthub.domain.spec.team;

import br.com.senior.prompthub.domain.enums.EntityStatus;
import br.com.senior.prompthub.domain.spec.UserContextAware;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamSearch implements UserContextAware {
    private String query;

    @Setter(AccessLevel.PRIVATE)
    private Long userId;

    @Setter(AccessLevel.PRIVATE)
    private List<Long> userTeamsIds;

    private List<EntityStatus> status;

    @Override
    public void setCurrentUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public void setAccessibleTeamIds(List<Long> teamsIds) {
        this.userTeamsIds = teamsIds;
    }
}
