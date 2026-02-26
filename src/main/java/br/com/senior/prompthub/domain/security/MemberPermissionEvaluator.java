package br.com.senior.prompthub.domain.security;

import br.com.senior.prompthub.domain.enums.TeamRole;
import br.com.senior.prompthub.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("memberPermissionEvaluator")
public class MemberPermissionEvaluator extends BasePermissionEvaluator {

    public MemberPermissionEvaluator(UserRepository userRepository) {
        super(userRepository);
    }

    @Override
    public boolean canList() {
        return isAdmin();
    }

    @Override
    protected boolean canViewAsUser(Long teamId) {
        return canAccessResource(null, teamId, TeamRole.VIEWER, TeamRole.DEV, TeamRole.TEAM_OWNER);
    }

    @Override
    protected boolean canCreateAsUser(Object teamId) {
        return canAccessResource(null, (Long) teamId, TeamRole.TEAM_OWNER);
    }

    @Override
    protected boolean canEditAsUser(Long teamId) {
        return canAccessResource(null, teamId, TeamRole.TEAM_OWNER);
    }

    @Override
    protected boolean canDeleteAsUser(Long teamId) {
        return canAccessResource(null, teamId, TeamRole.TEAM_OWNER);
    }

    public boolean canChangeRole() {
        return isAdmin();
    }
}
