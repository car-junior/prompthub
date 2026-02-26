package br.com.senior.prompthub.domain.spec;

import br.com.senior.prompthub.config.security.SecurityUtils;
import br.com.senior.prompthub.domain.enums.GlobalRole;
import br.com.senior.prompthub.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class UserContextEnricherAspect {
    private final UserRepository userRepository;

    /**
     * Intercepta chamadas de controllers que recebem objetos UserContextAware
     * e enriquece automaticamente com userId e teamIds do usuário logado.
     * <p>
     * IMPORTANTE: Só aplica filtros se o usuário NÃO for ADMIN.
     * Admins veem tudo sem filtros.
     */
    @Before("execution(* br.com.senior.prompthub.domain.controller..*.*(..))")
    public void enrichSearchWithUserContext(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            // Verifica se o parâmetro implementa UserContextAware
            if (!(arg instanceof UserContextAware contextAware)) {
                continue;
            }

            // Pega o usuário atual
            var currentUser = SecurityUtils.getCurrentUser();
            if (currentUser.isEmpty()) {
                continue;
            }

            var user = userRepository.findById(currentUser.get().getId());

            user.ifPresent(it -> {
                if (it.getRole() != GlobalRole.ADMIN) {
                    contextAware.setCurrentUserId(it.getId());
                    contextAware.setAccessibleTeamIds(it.getTeamsIds());
                }
            });
        }
    }
}
