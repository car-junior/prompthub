package br.com.senior.prompthub.domain.dto.user.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamRequest {
    @NotNull
    private Long id;
}
