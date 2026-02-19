package br.com.senior.prompthub.domain.service.team;

import br.com.senior.prompthub.core.dto.PageParams;
import br.com.senior.prompthub.core.dto.PageResult;
import br.com.senior.prompthub.core.repository.BaseRepository;
import br.com.senior.prompthub.core.service.AbstractBaseService;
import br.com.senior.prompthub.core.service.modelmapper.ModelMapperService;
import br.com.senior.prompthub.core.service.validate.CrudInterceptor;
import br.com.senior.prompthub.domain.dto.team.teamuser.input.AddMemberInput;
import br.com.senior.prompthub.domain.dto.team.teamuser.withmember.input.TeamWithMemberInput;
import br.com.senior.prompthub.domain.dto.team.teamuser.output.TeamMemberOutput;
import br.com.senior.prompthub.domain.dto.team.teamuser.withmember.output.TeamWithMemberOutput;
import br.com.senior.prompthub.domain.entity.Team;
import br.com.senior.prompthub.domain.entity.TeamUser;
import br.com.senior.prompthub.domain.entity.User;
import br.com.senior.prompthub.domain.enums.EntityStatus;
import br.com.senior.prompthub.domain.enums.TeamRole;
import br.com.senior.prompthub.domain.repository.TeamRepository;
import br.com.senior.prompthub.domain.repository.TeamUserRepository;
import br.com.senior.prompthub.domain.repository.UserRepository;
import br.com.senior.prompthub.domain.service.auth.PasswordGenerator;
import br.com.senior.prompthub.domain.service.teamuser.TeamUserService;
import br.com.senior.prompthub.domain.service.user.UserService;
import br.com.senior.prompthub.domain.service.user.UserValidator;
import br.com.senior.prompthub.domain.spec.teamuser.TeamUserSearch;
import br.com.senior.prompthub.domain.spec.teamuser.TeamUserSpecification;
import br.com.senior.prompthub.infrastructure.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static br.com.senior.prompthub.utils.GeneralUtils.getPageRequest;

@Service
@RequiredArgsConstructor
public class TeamService extends AbstractBaseService<Team, Long> {
    private final UserService userService;
    private final TeamValidator teamValidator;
    private final UserValidator userValidator;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamUserService teamUserService;
    private final PasswordGenerator passwordGenerator;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TeamUserRepository teamUserRepository;
    private final TeamUserSpecification teamUserSpecification;
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

    @Transactional
    public TeamWithMemberOutput createWithMembers(TeamWithMemberInput teamCreate) {
        var team = modelMapperService.convert(teamCreate, Team.class);
        var members = team.cloneMembers();
        validateAndSaveTeam(team);
        saveMembers(team, members);
        return modelMapperService.toObject(TeamWithMemberOutput.class, team);
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

    @Transactional(readOnly = true)
    public PageResult<TeamMemberOutput> getMembers(Long teamId, PageParams pageParams, TeamUserSearch teamUserSearch) {
        var pageRequest = getPageRequest(pageParams);
        var predicate = teamUserSpecification.getPredicate(teamId, teamUserSearch);
        var members = teamUserRepository.findAll(predicate, pageRequest);
        return modelMapperService.toPage(TeamMemberOutput.class, members);
    }

    @Transactional
    public void addMember(Long teamId, AddMemberInput member) {
        var team = getById(teamId);
        var user = userService.getById(member.getUserId());
        var teamUser = TeamUser.builder().team(team).user(user).role(member.getRole()).build();
        teamUserRepository.save(teamUser);
    }

    @Transactional
    public void updateMemberRole(Long teamId, Long userId, TeamRole role) {
        var member = teamUserService.getTeamUserByTeamIdAndUserId(teamId, userId);
        member.setRole(role);
        teamUserRepository.save(member);
    }

    @Transactional
    public void deleteMember(Long teamId, Long userId) {
        var member = teamUserService.getTeamUserByTeamIdAndUserId(teamId, userId);
        teamUserRepository.delete(member);
    }

    private void validateAndSaveTeam(Team team) {
        teamValidator.assertTeamNameUniqueness(team.getName(), team.getId());
        team.clearMembers();
        teamRepository.save(team);
    }

    private void saveMembers(Team team, List<TeamUser> members) {
        members.forEach(member -> {
            member.setTeam(team);
            saveUser(team, member.getUser());
        });
        teamUserRepository.saveAll(members);
        team.addAllMembers(members);
    }

    private void saveUser(Team team, User user) {
        validateUser(team, user);
        var tempPassword = passwordGenerator.generateTemporaryPassword();
        user.setPassword(passwordEncoder.encode(tempPassword));
        user.setTempPassword(tempPassword);
        user.setMustChangePassword(true);
        userRepository.save(user);
    }

    private void validateUser(Team team, User user) {
        userValidator.validateUsernameUniqueness(user.getUsername(), team.getId());
        userValidator.validateEmailUniqueness(user.getEmail(), team.getId());
    }
}
