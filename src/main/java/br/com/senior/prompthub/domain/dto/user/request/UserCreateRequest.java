package br.com.senior.prompthub.domain.dto.user.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {
    @NotBlank
    private String username;

    @Email
    private String email;

    @Valid
    @Builder.Default
    private List<TeamUserRequest> teams = new ArrayList<>();
}
