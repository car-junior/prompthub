package br.com.senior.prompthub.domain.controller;

import br.com.senior.prompthub.core.controller.BaseCrudController;
import br.com.senior.prompthub.core.dto.PageParams;
import br.com.senior.prompthub.core.dto.PageResult;
import br.com.senior.prompthub.core.service.modelmapper.ModelMapperService;
import br.com.senior.prompthub.domain.dto.team.teamuser.input.AddMemberInput;
import br.com.senior.prompthub.domain.dto.team.TeamInput;
import br.com.senior.prompthub.domain.dto.team.teamuser.withmember.input.TeamWithMemberInput;
import br.com.senior.prompthub.domain.dto.team.teamuser.output.TeamMemberOutput;
import br.com.senior.prompthub.domain.dto.team.TeamOutput;
import br.com.senior.prompthub.domain.dto.team.teamuser.withmember.output.TeamWithMemberOutput;
import br.com.senior.prompthub.domain.entity.Team;
import br.com.senior.prompthub.domain.enums.EntityStatus;
import br.com.senior.prompthub.domain.enums.TeamRole;
import br.com.senior.prompthub.domain.service.team.TeamService;
import br.com.senior.prompthub.domain.spec.team.TeamSearch;
import br.com.senior.prompthub.domain.spec.team.TeamSpecification;
import br.com.senior.prompthub.domain.spec.teamuser.TeamUserSearch;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("@teamPermissionEvaluator.isAdmin()")
    public ResponseEntity<PageResult<TeamOutput>> getAllTeams(PageParams pageParams, TeamSearch search) {
        return crudController.getAllSpec(pageParams, teamSpecification, search).asPageDto(TeamOutput.class);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@teamPermissionEvaluator.isAdmin() or @teamPermissionEvaluator.isTeamMember(#id)")
    public ResponseEntity<TeamOutput> getTeamById(@PathVariable Long id) {
        return crudController.getById(id).asDto(TeamOutput.class);
    }

    @PostMapping
    @PreAuthorize("@teamPermissionEvaluator.isAdmin()")
    public ResponseEntity<TeamOutput> createTeam(@Valid @RequestBody TeamInput teamCreate) {
        return crudController.create(teamCreate).asDto(TeamOutput.class);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@teamPermissionEvaluator.isAdmin() or @teamPermissionEvaluator.isTeamOwner(#id)")
    public ResponseEntity<TeamOutput> updateTeam(@PathVariable Long id, @Valid @RequestBody TeamInput teamCreate) {
        return crudController.update(id, teamCreate).asDto(TeamOutput.class);
    }

    @PatchMapping("/{id}/change-status")
    @PreAuthorize("@teamPermissionEvaluator.isAdmin() or @teamPermissionEvaluator.isTeamOwner(#id)")
    public ResponseEntity<Void> changeStatus(@PathVariable Long id, @RequestParam EntityStatus status) {
        teamService.changeStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/with-members")
    @PreAuthorize("@teamPermissionEvaluator.isAdmin()")
    public ResponseEntity<TeamWithMemberOutput> createTeamWithMembers(@Valid @RequestBody TeamWithMemberInput teamCreate) {
        var teamWithMembers = teamService.createWithMembers(teamCreate);
        return ResponseEntity.ok(teamWithMembers);
    }

    @GetMapping("/{teamId}/members")
    @PreAuthorize("@teamPermissionEvaluator.isAdmin() or @teamPermissionEvaluator.isTeamMember(#teamId)")
    public ResponseEntity<PageResult<TeamMemberOutput>> getTeamMembers(@PathVariable Long teamId,
                                                                       PageParams pageParams,
                                                                       TeamUserSearch search) {
        var members = teamService.getMembers(teamId, pageParams, search);
        return ResponseEntity.ok(members);
    }

    @PostMapping("/{teamId}/members")
    @PreAuthorize("@teamPermissionEvaluator.isAdmin() or @teamPermissionEvaluator.isTeamOwner(#teamId)")
    public ResponseEntity<Void> addMember(@PathVariable Long teamId, @Valid @RequestBody AddMemberInput member) {
        teamService.addMember(teamId, member);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{teamId}/members/{userId}")
    @PreAuthorize("@teamPermissionEvaluator.isAdmin() or @teamPermissionEvaluator.isTeamOwner(#teamId)")
    public ResponseEntity<Void> updateMemberRole(@PathVariable Long teamId, @PathVariable Long userId, @RequestParam TeamRole role) {
        teamService.updateMemberRole(teamId, userId, role);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{teamId}/members/{userId}")
    @PreAuthorize("@teamPermissionEvaluator.isAdmin() or @teamPermissionEvaluator.isTeamOwner(#teamId)")
    public ResponseEntity<Void> deleteMember(@PathVariable Long teamId, @PathVariable Long userId) {
        teamService.deleteMember(teamId, userId);
        return ResponseEntity.noContent().build();
    }
}
