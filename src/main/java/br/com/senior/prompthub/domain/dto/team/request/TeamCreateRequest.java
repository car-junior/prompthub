package br.com.senior.prompthub.domain.dto.team.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamCreateRequest {
    @NotBlank
    private String name;

    private String description;

    @Valid
    @Builder.Default
    private List<TeamUserRequest> members = new ArrayList<>();
}
