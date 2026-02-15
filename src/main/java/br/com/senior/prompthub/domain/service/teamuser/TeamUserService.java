package br.com.senior.prompthub.domain.service.teamuser;

import br.com.senior.prompthub.core.repository.BaseRepository;
import br.com.senior.prompthub.core.service.AbstractBaseService;
import br.com.senior.prompthub.core.service.modelmapper.ModelMapperService;
import br.com.senior.prompthub.core.service.validate.CrudInterceptor;
import br.com.senior.prompthub.domain.entity.TeamUser;
import br.com.senior.prompthub.domain.repository.TeamUserRepository;
import br.com.senior.prompthub.infrastructure.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamUserService extends AbstractBaseService<TeamUser, Long> {
    private final TeamUserRepository teamUserRepository;
    private final ModelMapperService<TeamUser> modelMapperService;

    @Override
    protected BaseRepository<TeamUser, Long> getRepository() {
        return teamUserRepository;
    }

    @Override
    protected ModelMapperService<TeamUser> getModelMapperService() {
        return modelMapperService;
    }

    @Override
    protected CrudInterceptor<TeamUser, Long> crudInterceptor() {
        return null;
    }

    @Transactional(readOnly = true)
    public TeamUser getTeamUserByTeamIdAndUserId(Long teamId, Long userId) {
        return teamUserRepository.findByTeamIdAndUserId(teamId, userId)
                .orElseThrow(() -> CustomException.badRequest("Nenhum usuário encontrado para o time informado."));
    }

}
