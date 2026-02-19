package br.com.senior.prompthub.domain.dto.team.teamuser.output;

import br.com.senior.prompthub.domain.enums.TeamRole;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberOutput {
    private long id;
    private UserOutput user;
    private TeamRole role;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
