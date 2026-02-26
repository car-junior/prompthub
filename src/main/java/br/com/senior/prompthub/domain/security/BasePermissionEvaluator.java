package br.com.senior.prompthub.domain.security;

import br.com.senior.prompthub.config.security.SecurityUtils;
import br.com.senior.prompthub.config.security.UserPrincipal;
import br.com.senior.prompthub.domain.enums.GlobalRole;
import br.com.senior.prompthub.domain.enums.TeamRole;
import br.com.senior.prompthub.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.EnumSet;
import java.util.List;
import java.util.function.BooleanSupplier;

/**
 * Classe base para Permission Evaluators com métodos auxiliares genéricos
 * para reduzir código repetitivo nas validações.
 */

@Slf4j
@RequiredArgsConstructor
public abstract class BasePermissionEvaluator {
    protected final UserRepository userRepository;

    protected boolean isAdmin() {
        var principal = SecurityUtils.getCurrentUser();
        if (principal.isEmpty()) {
            return false;
        }
        var user = userRepository.findById(principal.get().getId());
        return user.map(u -> u.getRole() == GlobalRole.ADMIN).orElse(false);
    }

    protected boolean isAuthenticated() {
        return SecurityUtils.getCurrentUser().isPresent();
    }

    protected Long getCurrentUserId() {
        var principal = SecurityUtils.getCurrentUser();
        return principal.map(UserPrincipal::getId).orElse(null);
    }

    // ============================================
    // MÉTODOS AUXILIARES GENÉRICOS
    // ============================================

    /**
     * Verifica se o usuário atual é dono do recurso
     *
     * @param ownerId ID do dono do recurso
     */
    protected boolean isOwner(Long ownerId) {
        Long currentUserId = getCurrentUserId();
        return currentUserId != null && currentUserId.equals(ownerId);
    }

    /**
     * Verifica se o usuário atual é membro de um time específico
     *
     * @param teamId ID do time
     */
    protected boolean isTeamMember(Long teamId) {
        Long userId = getCurrentUserId();
        if (userId == null) {
            return false;
        }
        var user = userRepository.findById(userId);
        return user.map(it -> it.getTeamsIds().contains(teamId)).orElse(false);
    }

    /**
     * Verifica se o usuário atual tem uma role específica em um time
     *
     * @param teamId ID do time
     * @param role   Role necessária
     */
    protected boolean hasTeamRole(Long teamId, TeamRole role) {
        Long userId = getCurrentUserId();
        if (userId == null) {
            return false;
        }
        return userRepository.userHasTeamRole(userId, teamId, EnumSet.of(role));
    }

    /**
     * Verifica se o usuário atual tem uma das roles especificadas em um time
     *
     * @param teamId ID do time
     * @param roles  Roles aceitas
     */
    protected boolean hasAnyTeamRole(Long teamId, TeamRole... roles) {
        Long userId = getCurrentUserId();
        if (userId == null) {
            return false;
        }
        return userRepository.userHasTeamRole(userId, teamId, EnumSet.copyOf(List.of(roles)));
    }

    /**
     * Executa validação customizada com fallback para ADMIN
     *
     * @param validation Função de validação para usuários não-ADMIN
     */
    protected boolean checkPermission(BooleanSupplier validation) {
        if (isAdmin()) {
            return true;
        }
        return validation.getAsBoolean();
    }

    /**
     * Verifica se o recurso pertence ao usuário OU se ele tem permissão no time
     *
     * @param ownerId   ID do dono do recurso (pode ser null)
     * @param teamId    ID do time do recurso (pode ser null)
     * @param teamRoles Roles aceitas no time
     */
    protected boolean canAccessResource(Long ownerId, Long teamId, TeamRole... teamRoles) {
        if (isAdmin()) {
            return true;
        }

        // Verifica se é o dono
        if (ownerId != null && isOwner(ownerId)) {
            return true;
        }

        // Verifica se tem permissão no time
        return teamId != null && hasAnyTeamRole(teamId, teamRoles);
    }

    // ============================================
    // MÉTODOS PADRÕES (podem ser sobrescritos)
    // ============================================

    public boolean canList() {
        return checkPermission(this::isAuthenticated);
    }

    public boolean canView(Long resourceId) {
        return checkPermission(() -> canViewAsUser(resourceId));
    }

    public boolean canCreate(Object resource) {
        return checkPermission(() -> canCreateAsUser(resource));
    }

    public boolean canEdit(Long resourceId) {
        return checkPermission(() -> canEditAsUser(resourceId));
    }

    public boolean canDelete(Long resourceId) {
        return checkPermission(() -> canDeleteAsUser(resourceId));
    }

    // ============================================
    // MÉTODOS ABSTRATOS
    // ============================================

    protected abstract boolean canViewAsUser(Long resourceId);

    protected abstract boolean canCreateAsUser(Object resource);

    protected abstract boolean canEditAsUser(Long resourceId);

    protected abstract boolean canDeleteAsUser(Long resourceId);
}
