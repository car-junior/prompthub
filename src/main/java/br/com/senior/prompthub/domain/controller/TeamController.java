package br.com.senior.prompthub.domain.controller;

import br.com.senior.prompthub.core.controller.BaseCrudController;
import br.com.senior.prompthub.core.dto.PageParams;
import br.com.senior.prompthub.core.dto.PageResult;
import br.com.senior.prompthub.core.service.modelmapper.ModelMapperService;
import br.com.senior.prompthub.domain.dto.team.response.TeamResponse;
import br.com.senior.prompthub.domain.dto.team.request.TeamCreateRequest;
import br.com.senior.prompthub.domain.entity.Team;
import br.com.senior.prompthub.domain.enums.EntityStatus;
import br.com.senior.prompthub.domain.service.team.TeamService;
import br.com.senior.prompthub.domain.spec.team.TeamSearch;
import br.com.senior.prompthub.domain.spec.team.TeamSpecification;
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
}
