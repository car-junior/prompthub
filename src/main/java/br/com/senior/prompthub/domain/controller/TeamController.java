package br.com.senior.prompthub.domain.controller;

import br.com.senior.prompthub.core.controller.BaseCrudController;
import br.com.senior.prompthub.core.dto.PageParams;
import br.com.senior.prompthub.core.dto.PageResult;
import br.com.senior.prompthub.core.service.modelmapper.ModelMapperService;
import br.com.senior.prompthub.domain.dto.team.request.AddMemberRequest;
import br.com.senior.prompthub.domain.dto.team.request.TeamCreateRequest;
import br.com.senior.prompthub.domain.dto.team.response.MemberResponse;
import br.com.senior.prompthub.domain.dto.team.response.TeamResponse;
import br.com.senior.prompthub.domain.entity.Team;
import br.com.senior.prompthub.domain.enums.EntityStatus;
import br.com.senior.prompthub.domain.enums.UserRole;
import br.com.senior.prompthub.domain.service.team.TeamService;
import br.com.senior.prompthub.domain.spec.team.TeamSearch;
import br.com.senior.prompthub.domain.spec.team.TeamSpecification;
import br.com.senior.prompthub.domain.spec.teamuser.TeamUserSearch;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teams")
public class TeamController {
    private final TeamService teamService;
    private final TeamSpecification teamSpecification;
    private final BaseCrudController<Team, Long> crudController;

    public TeamController(TeamService teamService,
                          ModelMapperService<Team> teamModelMapperService,
                          TeamSpecification teamSpecification) {
        this.teamSpecification = teamSpecification;
        this.teamService = teamService;
        this.crudController = new BaseCrudController<>(teamService, teamModelMapperService, Team.class);
    }

    @GetMapping("")
    public ResponseEntity<PageResult<TeamResponse>> getAllTeams(PageParams pageParams, TeamSearch search) {
        return crudController.getAllSpec(pageParams, teamSpecification, search).asPageDto(TeamResponse.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamResponse> getTeamById(@PathVariable Long id) {
        return crudController.getById(id).asDto(TeamResponse.class);
    }

    @PostMapping
    public ResponseEntity<TeamResponse> createTeam(@Valid @RequestBody TeamCreateRequest teamDTO) {
        return crudController.create(teamDTO).asDto(TeamResponse.class);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamResponse> updateTeam(@PathVariable Long id, @Valid @RequestBody TeamCreateRequest teamDTO) {
        return crudController.update(id, teamDTO).asDto(TeamResponse.class);
    }

    @PatchMapping("/{id}/change-status")
    public ResponseEntity<Void> changeStatus(@PathVariable Long id, @RequestParam EntityStatus status) {
        teamService.changeStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{teamId}/members")
    public ResponseEntity<PageResult<MemberResponse>> getTeamMembers(@PathVariable Long teamId,
                                                                     PageParams pageParams,
                                                                     TeamUserSearch search) {
        var members = teamService.getMembers(teamId, pageParams, search);
        return ResponseEntity.ok(members);
    }

    // Adicionar membros ao time
    @PostMapping("/{teamId}/members")
    public ResponseEntity<Void> addMembers(@PathVariable Long teamId, @Valid @RequestBody AddMemberRequest member) {
        teamService.addMembers(teamId, member);
        return ResponseEntity.noContent().build();
    }

    // Atualizar role do membro
    @PatchMapping("/{teamId}/members/{userId}")
    public ResponseEntity<Void> updateMemberRole(@PathVariable Long teamId, @PathVariable Long userId, @RequestParam UserRole role) {
        teamService.updateMemberRole(teamId, userId, role);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{teamId}/members/{userId}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long teamId, @PathVariable Long userId) {
        teamService.deleteMember(teamId, userId);
        return ResponseEntity.noContent().build();
    }
}
