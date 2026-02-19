package br.com.senior.prompthub.domain.dto.team.teamuser.withmember.output;

import br.com.senior.prompthub.domain.enums.TeamRole;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class TeamMemberOutput {
    private long id;
    private UserOutput user;
    private TeamRole role;
}
