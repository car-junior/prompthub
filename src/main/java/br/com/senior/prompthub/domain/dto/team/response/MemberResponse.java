package br.com.senior.prompthub.domain.dto.team.response;

import br.com.senior.prompthub.domain.enums.UserRole;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {
    private long id;
    private UserResponse user;
    private UserRole role;
}
