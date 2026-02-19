package br.com.senior.prompthub.domain.dto.team.teamuser.withmember.output;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOutput {
    private Long id;
    private String username;
    private String email;
    private String tempPassword;
}
