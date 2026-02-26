package br.com.senior.prompthub.domain.security;

import br.com.senior.prompthub.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Bean que avalia permissões relacionadas a times.
 * Usado nas anotações @PreAuthorize dos controllers.
 */
@Slf4j
@Component("userPermissionEvaluator")
public class UserPermissionEvaluator extends BasePermissionEvaluator {

    public UserPermissionEvaluator(UserRepository userRepository) {
        super(userRepository);
    }

    @Override
    public boolean canList() {
        return isAdmin();
    }

    @Override
    protected boolean canViewAsUser(Long userId) {
        if (isAdmin()) {
            return true;
        }
        return Objects.equals(userId, getCurrentUserId());
    }

    @Override
    protected boolean canCreateAsUser(Object resource) {
        return false;
    }

    @Override
    protected boolean canEditAsUser(Long userId) {
        return Objects.equals(userId, getCurrentUserId());
    }

    @Override
    protected boolean canDeleteAsUser(Long resourceId) {
        return false;
    }

    public boolean canChangeStatus() {
        return isAdmin();
    }

    public boolean canChangeRole() {
        return isAdmin();
    }
}
