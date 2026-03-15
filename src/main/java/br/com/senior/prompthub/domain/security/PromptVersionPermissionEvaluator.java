package br.com.senior.prompthub.domain.security;

import br.com.senior.prompthub.domain.dto.promptversion.input.PromptVersionInput;
import br.com.senior.prompthub.domain.entity.Team;
import br.com.senior.prompthub.domain.entity.User;
import br.com.senior.prompthub.domain.enums.TeamRole;
import br.com.senior.prompthub.domain.repository.PromptRepository;
import br.com.senior.prompthub.domain.repository.PromptVersionRepository;
import br.com.senior.prompthub.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Component("promptVersionPermissionEvaluator")
public class PromptVersionPermissionEvaluator extends BasePermissionEvaluator {

    private final PromptRepository promptRepository;
    private final PromptVersionRepository promptVersionRepository;

    public PromptVersionPermissionEvaluator(UserRepository userRepository,
                                            PromptRepository promptRepository,
                                            PromptVersionRepository promptVersionRepository) {
        super(userRepository);
        this.promptRepository = promptRepository;
        this.promptVersionRepository = promptVersionRepository;
    }

    @Override
    protected boolean canViewAsUser(Long versionId) {
        var canView = new AtomicBoolean(false);
        promptVersionRepository.findById(versionId).ifPresent(version -> {
            var ownerId = Optional.ofNullable(version.getPrompt().getOwner()).map(User::getId).orElse(null);
            var teamId = Optional.ofNullable(version.getPrompt().getTeam()).map(Team::getId).orElse(null);
            canView.set(canAccessResource(ownerId, teamId, TeamRole.VIEWER, TeamRole.DEV, TeamRole.TEAM_OWNER));
        });
        return canView.get();
    }

    @Override
    protected boolean canCreateAsUser(Object input) {
        var versionInput = (PromptVersionInput) input;
        var canCreate = new AtomicBoolean(false);
        promptRepository.findById(versionInput.getPrompt().getId()).ifPresent(prompt -> {
            var ownerId = Optional.ofNullable(prompt.getOwner()).map(User::getId).orElse(null);
            var teamId = Optional.ofNullable(prompt.getTeam()).map(Team::getId).orElse(null);
            canCreate.set(canAccessResource(ownerId, teamId, TeamRole.DEV, TeamRole.TEAM_OWNER));
        });
        return canCreate.get();
    }

    @Override
    protected boolean canEditAsUser(Long versionId) {
        var canEdit = new AtomicBoolean(false);
        promptVersionRepository.findById(versionId).ifPresent(version -> {
            var ownerId = Optional.ofNullable(version.getPrompt().getOwner()).map(User::getId).orElse(null);
            var teamId = Optional.ofNullable(version.getPrompt().getTeam()).map(Team::getId).orElse(null);
            canEdit.set(canAccessResource(ownerId, teamId, TeamRole.DEV, TeamRole.TEAM_OWNER));
        });
        return canEdit.get();
    }

    @Override
    protected boolean canDeleteAsUser(Long versionId) {
        var canDelete = new AtomicBoolean(false);
        promptVersionRepository.findById(versionId).ifPresent(version -> {
            var ownerId = Optional.ofNullable(version.getPrompt().getOwner()).map(User::getId).orElse(null);
            var teamId = Optional.ofNullable(version.getPrompt().getTeam()).map(Team::getId).orElse(null);
            canDelete.set(canAccessResource(ownerId, teamId, TeamRole.TEAM_OWNER));
        });
        return canDelete.get();
    }
}
