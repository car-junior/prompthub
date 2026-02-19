package br.com.senior.prompthub.domain.dto.team.teamuser.withmember.input;

import br.com.senior.prompthub.domain.enums.EntityStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class UserInput {
    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    @Setter(AccessLevel.NONE)
    private Boolean mustChangePassword = true;

    @Setter(AccessLevel.NONE)
    private EntityStatus status = EntityStatus.INACTIVE;
}