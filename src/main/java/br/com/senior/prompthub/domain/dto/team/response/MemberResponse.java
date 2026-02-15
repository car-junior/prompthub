package br.com.senior.prompthub.domain.dto.team.response;

import br.com.senior.prompthub.domain.enums.UserRole;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {
    private long id;
    private UserResponse user;
    private UserRole role;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
