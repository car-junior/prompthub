package br.com.senior.prompthub.domain.security;

import br.com.senior.prompthub.config.security.UserPrincipal;
import br.com.senior.prompthub.domain.enums.TeamRole;
import br.com.senior.prompthub.domain.repository.TeamUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Bean que avalia permissões relacionadas a times.
 * Usado nas anotações @PreAuthorize dos controllers.
 */
@Slf4j
@RequiredArgsConstructor
@Component("teamPermissionEvaluator")
public class TeamPermissionEvaluator extends BasePermissionEvaluator {

    private final TeamUserRepository teamUserRepository;

    /**
     * Verifica se o usuário logado é membro do time.
     * <p>
     * Exemplo de uso:
     *
     * @PreAuthorize("@teamPermissionEvaluator.isTeamMember(#teamId)") Fluxo:
     * 1. Pega o usuário logado do SecurityContext
     * 2. Busca no banco se existe um registro TeamUser com teamId e userId
     * 3. Retorna true se encontrar, false caso contrário
     */
    public boolean isTeamMember(Long teamId) {
        UserPrincipal principal = getCurrentUser();
        if (principal == null) {
            log.info("Nenhum usuário autenticado encontrado");
            return false;
        }
        // Busca no banco se o usuário pertence ao time
        return teamUserRepository.findByTeamIdAndUserId(teamId, principal.getId()).isPresent();
    }

    /**
     * Verifica se o usuário logado é TEAM_OWNER do time.
     */
    public boolean isTeamOwner(Long teamId) {
        UserPrincipal principal = getCurrentUser();
        if (principal == null) {
            return false;
        }
        var teamUser = teamUserRepository.findByTeamIdAndUserId(teamId, principal.getId());
        return teamUser.map(it -> it.getRole() == TeamRole.TEAM_OWNER).orElse(false);
    }
}
