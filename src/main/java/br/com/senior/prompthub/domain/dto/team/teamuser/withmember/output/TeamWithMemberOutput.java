package br.com.senior.prompthub.domain.dto.team.teamuser.withmember.output;

import br.com.senior.prompthub.domain.enums.EntityStatus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamWithMemberOutput {
    private String name;
    private String description;
    private EntityStatus status;
    private List<TeamMemberOutput> members;
}
