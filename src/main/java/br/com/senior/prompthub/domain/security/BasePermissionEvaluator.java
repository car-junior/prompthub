package br.com.senior.prompthub.domain.security;

import br.com.senior.prompthub.config.security.UserPrincipal;
import br.com.senior.prompthub.domain.enums.GlobalRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
abstract class BasePermissionEvaluator {
    protected UserPrincipal getCurrentUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof UserPrincipal) {
            return (UserPrincipal) auth.getPrincipal();
        }
        return null;
    }

    public boolean isAdmin() {
        UserPrincipal user = getCurrentUser();
        return user != null && isRoleAdmin(user);
    }

    private static boolean isRoleAdmin(UserPrincipal user) {
        return user.getAuthorities()
                .stream()
                .anyMatch(it -> it.toString().equals(GlobalRole.ADMIN.getRoleAuthority()));
    }
}