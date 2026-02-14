package br.com.senior.prompthub.domain.dto.team.response;

import br.com.senior.prompthub.domain.enums.EntityStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamResponse {
    private long id;
    private String name;
    private String description;
    private EntityStatus status;
    private List<MemberResponse> members;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
