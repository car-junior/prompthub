package br.com.senior.prompthub.domain.dto.user.response;

import br.com.senior.prompthub.domain.enums.EntityStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private long id;
    private String username;
    private String email;
    private EntityStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
