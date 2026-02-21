package br.com.senior.prompthub.domain.security;

import br.com.senior.prompthub.config.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Bean que avalia permissões relacionadas a times.
 * Usado nas anotações @PreAuthorize dos controllers.
 */
@Slf4j
@RequiredArgsConstructor
@Component("userPermissionEvaluator")
public class UserPermissionEvaluator extends BasePermissionEvaluator {
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
    public boolean isUser(Long userId) {
        UserPrincipal principal = getCurrentUser();
        if (principal == null) {
            log.info("Nenhum usuário autenticado encontrado");
            return false;
        }
        return userId.equals(principal.getId());
    }
}
