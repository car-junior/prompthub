package br.com.senior.prompthub.domain.security;

import br.com.senior.prompthub.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Tags são recursos globais: qualquer autenticado pode listar/visualizar,
 * apenas ADMIN pode criar, editar e deletar.
 */
@Slf4j
@Component("tagPermissionEvaluator")
public class TagPermissionEvaluator extends BasePermissionEvaluator {

    public TagPermissionEvaluator(UserRepository userRepository) {
        super(userRepository);
    }

    @Override
    public boolean canList() {
        return isAuthenticated();
    }

    @Override
    protected boolean canViewAsUser(Long resourceId) {
        return isAdmin();
    }

    @Override
    protected boolean canCreateAsUser(Object resource) {
        return isAdmin();
    }

    @Override
    protected boolean canEditAsUser(Long resourceId) {
        return isAdmin();
    }

    @Override
    protected boolean canDeleteAsUser(Long resourceId) {
        return isAdmin();
    }
}
