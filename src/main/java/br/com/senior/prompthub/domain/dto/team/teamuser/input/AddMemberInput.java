package br.com.senior.prompthub.domain.dto.team.teamuser.input;

import br.com.senior.prompthub.domain.enums.TeamRole;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddMemberInput {
    @NotNull
    private Long userId;

    @NotNull
    private TeamRole role;
}
