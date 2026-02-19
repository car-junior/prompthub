package br.com.senior.prompthub.domain.dto.team.teamuser.withmember.input;

import br.com.senior.prompthub.domain.enums.TeamRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class TeamUserInput {
    @Valid
    @NotNull
    private UserInput user;

    @NotNull
    private TeamRole role;
}
