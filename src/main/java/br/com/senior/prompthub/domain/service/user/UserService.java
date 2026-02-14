package br.com.senior.prompthub.domain.service.user;

import br.com.senior.prompthub.core.repository.BaseRepository;
import br.com.senior.prompthub.core.service.AbstractBaseService;
import br.com.senior.prompthub.core.service.modelmapper.ModelMapperService;
import br.com.senior.prompthub.core.service.validate.CrudInterceptor;
import br.com.senior.prompthub.domain.entity.TeamUser;
import br.com.senior.prompthub.domain.entity.User;
import br.com.senior.prompthub.domain.enums.EntityStatus;
import br.com.senior.prompthub.domain.repository.TeamUserRepository;
import br.com.senior.prompthub.domain.repository.UserRepository;
import br.com.senior.prompthub.domain.service.team.TeamValidator;
import br.com.senior.prompthub.infrastructure.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService extends AbstractBaseService<User, Long> {
    private final UserValidator userValidator;
    private final TeamValidator teamValidator;
    private final UserRepository userRepository;
    private final TeamUserRepository teamUserRepository;
    private final ModelMapperService<User> modelMapperService;

    @Override
    protected BaseRepository<User, Long> getRepository() {
        return userRepository;
    }

    @Override
    protected ModelMapperService<User> getModelMapperService() {
        return modelMapperService;
    }

    @Override
    protected CrudInterceptor<User, Long> crudInterceptor() {
        return null;
    }

    @Override
    @Transactional
    public User create(User user) {
        validateTeamsExist(user.getTeams());
        var teamUsers = user.cloneTeams();
        validateUser(user);
        saveUser(user);
        associateUserToTeams(user, teamUsers);
        return user;
    }

    private void validateTeamsExist(List<TeamUser> teams) {
        teams.forEach(teamUser -> {
            teamValidator.assertExistsTeamById(teamUser.getTeam().getId());
        });
    }

    @Transactional
    public void changeStatus(Long id, EntityStatus status) {
        var user = getById(id);
        if (user.getStatus().equals(EntityStatus.DELETED)) {
            throw CustomException.badRequest("Não é possível alterar o status de um usuário deletado.");
        }
        user.setStatus(status);
        userRepository.save(user);
    }

    private void validateUser(User user) {
        userValidator.validateUsernameUniqueness(user.getUsername(), user.getId());
    }

    private void saveUser(User user) {
        user.clearTeams();
        user.setPassword(null);
        userRepository.save(user);
    }

    private void associateUserToTeams(User user, List<TeamUser> teamUsers) {
        teamUsers.forEach(teamUser -> teamUser.setUser(user));
        user.addAllTeams(teamUsers);
        teamUserRepository.saveAll(teamUsers);
    }
}
