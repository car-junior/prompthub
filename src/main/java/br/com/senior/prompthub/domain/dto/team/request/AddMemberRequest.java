package br.com.senior.prompthub.domain.dto.team.request;

import br.com.senior.prompthub.domain.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddMemberRequest {
    @NotNull
    private Long userId;

    @NotNull
    private UserRole role;
}
