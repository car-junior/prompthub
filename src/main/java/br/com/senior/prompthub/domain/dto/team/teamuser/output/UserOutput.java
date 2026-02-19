package br.com.senior.prompthub.domain.dto.team.teamuser.output;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class UserOutput {
    private Long id;
    private String username;
    private String email;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
