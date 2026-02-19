package br.com.senior.prompthub.domain.dto.team;

import br.com.senior.prompthub.domain.enums.EntityStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamOutput {
    private long id;
    private String name;
    private String description;
    private EntityStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
