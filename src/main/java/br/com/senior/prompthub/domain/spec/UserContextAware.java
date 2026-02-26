package br.com.senior.prompthub.domain.spec;

import java.util.List;

public interface UserContextAware {
    void setCurrentUserId(Long userId);
    void setAccessibleTeamIds(List<Long> teamsIds);
}
