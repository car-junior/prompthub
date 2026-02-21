package br.com.senior.prompthub.domain.security;

import br.com.senior.prompthub.config.security.UserPrincipal;
import br.com.senior.prompthub.domain.dto.prompt.input.PromptInput;
import br.com.senior.prompthub.domain.entity.User;
import br.com.senior.prompthub.domain.enums.TeamRole;
import br.com.senior.prompthub.domain.repository.PromptRepository;
import br.com.senior.prompthub.domain.repository.TeamUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;
import java.util.Optional;

/**
 * Bean que avalia permissões relacionadas a times.
 * Usado nas anotações @PreAuthorize dos controllers.
 */
@Slf4j
@RequiredArgsConstructor
@Component("promptPermissionEvaluator")
public class PromptPermissionEvaluator extends BasePermissionEvaluator {
    private final PromptRepository promptRepository;
    private final TeamUserRepository teamUserRepository;
    private final EnumSet<TeamRole> rolesAllowed = EnumSet.of(TeamRole.TEAM_OWNER, TeamRole.DEV);

    @Transactional(readOnly = true)
    public boolean canCreate(PromptInput promptInput) {
        if (promptInput.getTeam() == null) {
            log.info("Prompt sem time, permitindo criação");
            return true; // Permite criação de prompt sem time
        }

        UserPrincipal principal = getCurrentUser();
        if (principal == null) {
            return false;
        }

        var teamUser = teamUserRepository.findByTeamIdAndUserId(promptInput.getTeam().getId(), principal.getId());

        return teamUser.map(it -> rolesAllowed.contains(it.getRole())).orElse(false);
    }

    @Transactional(readOnly = true)
    public boolean canEdit(Long promptId, PromptInput promptInput) {
        UserPrincipal principal = getCurrentUser();
        if (principal == null) {
            return false;
        }

        var prompt = promptRepository.findById(promptId).get();

        var ownerId = Optional.ofNullable(prompt.getOwner()).map(User::getId).orElse(null);

        // Permite edição se o usuário for o dono do prompt
        if (principal.getId().equals(ownerId)) {
            log.info("Usuário é dono do prompt, permitindo edição");
            return true;
        }

        return isTeamManagerOfPrompt(promptId, principal.getId());
    }

    private boolean isTeamManagerOfPrompt(Long promptId, Long userId) {
        return promptRepository.isTeamManagerOfPrompt(promptId, userId, rolesAllowed);
    }
}
