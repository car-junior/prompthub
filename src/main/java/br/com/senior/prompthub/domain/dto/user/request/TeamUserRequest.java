package br.com.senior.prompthub.domain.dto.user.request;

import br.com.senior.prompthub.domain.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamUserRequest {
    @NotNull
    private TeamRequest team;

    @NotBlank
    private UserRole role;
}
