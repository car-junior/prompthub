package br.com.senior.prompthub.domain.service.team;

import br.com.senior.prompthub.core.repository.BaseRepository;
import br.com.senior.prompthub.core.service.AbstractBaseService;
import br.com.senior.prompthub.core.service.modelmapper.ModelMapperService;
import br.com.senior.prompthub.core.service.validate.CrudInterceptor;
import br.com.senior.prompthub.domain.entity.Team;
import br.com.senior.prompthub.domain.entity.TeamUser;
import br.com.senior.prompthub.domain.enums.EntityStatus;
import br.com.senior.prompthub.domain.repository.TeamRepository;
import br.com.senior.prompthub.domain.repository.TeamUserRepository;
import br.com.senior.prompthub.domain.repository.UserRepository;
import br.com.senior.prompthub.domain.service.user.UserValidator;
import br.com.senior.prompthub.infrastructure.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService extends AbstractBaseService<Team, Long> {
    private final TeamValidator teamValidator;
    private final UserValidator userValidator;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamUserRepository teamUserRepository;
    private final ModelMapperService<Team> modelMapperService;

    @Override
    protected BaseRepository<Team, Long> getRepository() {
        return teamRepository;
    }

    @Override
    protected ModelMapperService<Team> getModelMapperService() {
        return modelMapperService;
    }

    @Override
    protected CrudInterceptor<Team, Long> crudInterceptor() {
        return null;
    }

    @Override
    @Transactional
    public Team create(Team team) {
        var members = team.cloneMembers();
        validateAndSaveTeam(team);
        validateAndSaveMembers(team, members);
        return team;
    }

    @Transactional
    public void changeStatus(Long id, EntityStatus status) {
        var team = getById(id);
        if (team.getStatus().equals(EntityStatus.DELETED)) {
            throw CustomException.badRequest("Não é possível alterar o status de um time deletado.");
        }
        team.setStatus(status);
        teamRepository.save(team);
    }

    private void validateAndSaveTeam(Team team) {
        teamValidator.assertTeamNameUniqueness(team.getName(), team.getId());
        team.clearMembers();
        teamRepository.save(team);
    }

    private void validateAndSaveMembers(Team team, List<TeamUser> members) {
        members.forEach(member -> {
            member.setTeam(team);
            var user = member.getUser();
            userValidator.validateUsernameUniqueness(user.getUsername(), team.getId());
            user.setPassword(null);
            userRepository.save(user);
        });
        teamUserRepository.saveAll(members);
        team.addAllMembers(members);
    }
}
