package br.com.senior.prompthub.domain.dto.user;

import br.com.senior.prompthub.domain.enums.EntityStatus;
import br.com.senior.prompthub.domain.enums.GlobalRole;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOutput {
    private long id;
    private String username;
    private String email;
    private GlobalRole role;
    private EntityStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
