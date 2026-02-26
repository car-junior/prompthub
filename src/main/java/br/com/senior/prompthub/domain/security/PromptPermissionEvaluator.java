package br.com.senior.prompthub.domain.security;

import br.com.senior.prompthub.domain.dto.prompt.input.PromptInput;
import br.com.senior.prompthub.domain.entity.Team;
import br.com.senior.prompthub.domain.entity.User;
import br.com.senior.prompthub.domain.enums.TeamRole;
import br.com.senior.prompthub.domain.repository.PromptRepository;
import br.com.senior.prompthub.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Bean que avalia permissões relacionadas a times.
 * Usado nas anotações @PreAuthorize dos controllers.
 */
@Slf4j
@Component("promptPermissionEvaluator")
public class PromptPermissionEvaluator extends BasePermissionEvaluator {
    private final PromptRepository promptRepository;

    public PromptPermissionEvaluator(UserRepository userRepository, PromptRepository promptRepository) {
        super(userRepository);
        this.promptRepository = promptRepository;
    }

    @Override
    protected boolean canViewAsUser(Long promptId) {
        var canView = new AtomicBoolean(false);
        promptRepository.findById(promptId).ifPresent(prompt -> {
            var ownerId = Optional.ofNullable(prompt.getOwner()).map(User::getId).orElse(null);
            var teamId = Optional.ofNullable(prompt.getTeam()).map(Team::getId).orElse(null);
            canView.set(canAccessResource(ownerId, teamId, TeamRole.VIEWER, TeamRole.DEV, TeamRole.TEAM_OWNER));
        });
        return canView.get();
    }

    @Override
    protected boolean canCreateAsUser(Object promptInput) {
        var prompt = (PromptInput) promptInput;
        if (prompt.getTeam() == null) {
            return true;
        }
        return canAccessResource(null, prompt.getTeam().getId(), TeamRole.DEV, TeamRole.TEAM_OWNER);
    }

    @Override
    protected boolean canEditAsUser(Long promptId) {
        var canAccess = new AtomicBoolean(false);
        promptRepository.findById(promptId).ifPresent(prompt -> {
            var ownerId = Optional.ofNullable(prompt.getOwner()).map(User::getId).orElse(null);
            var teamId = Optional.ofNullable(prompt.getTeam()).map(Team::getId).orElse(null);
            canAccess.set(canAccessResource(ownerId, teamId, TeamRole.DEV, TeamRole.TEAM_OWNER));
        });
        return canAccess.get();
    }

    @Override
    protected boolean canDeleteAsUser(Long promptId) {
        var canEdit = new AtomicBoolean(false);
        promptRepository.findById(promptId).ifPresent(prompt -> {
            var ownerId = Optional.ofNullable(prompt.getOwner()).map(User::getId).orElse(null);
            var teamId = Optional.ofNullable(prompt.getTeam()).map(Team::getId).orElse(null);
            canEdit.set(canAccessResource(ownerId, teamId, TeamRole.TEAM_OWNER));
        });
        return canEdit.get();
    }
}
