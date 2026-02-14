package br.com.senior.prompthub.domain.dto.team.request;

import br.com.senior.prompthub.domain.enums.UserRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamUserRequest {
    @Valid
    @NotNull
    private UserRequest user;

    @NotNull
    private UserRole role;
}
