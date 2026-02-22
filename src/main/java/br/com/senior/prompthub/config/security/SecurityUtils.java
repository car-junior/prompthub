package br.com.senior.prompthub.config.security;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@UtilityClass
public class SecurityUtils {

    /**
     * Retorna o usuário logado (opcional)
     */
    public static Optional<UserPrincipal> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserPrincipal) {
            return Optional.of((UserPrincipal) principal);
        }

        return Optional.empty();
    }

    /**
     * Retorna o ID do usuário logado
     * Lança exceção se não estiver autenticado
     */
    public static Long getCurrentUserId() {
        return getCurrentUser()
                .map(UserPrincipal::getId)
                .orElseThrow(() -> new IllegalStateException("Usuário não está autenticado"));
    }

    /**
     * Retorna o username do usuário logado
     */
    public static String getCurrentUsername() {
        return getCurrentUser()
                .map(UserPrincipal::getUsername)
                .orElseThrow(() -> new IllegalStateException("Usuário não está autenticado"));
    }

    /**
     * Verifica se o usuário logado é ADMIN
     */
    public static boolean isAdmin() {
        return getCurrentUser()
                .map(user -> user.getAuthorities().stream()
                        .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")))
                .orElse(false);
    }
}
