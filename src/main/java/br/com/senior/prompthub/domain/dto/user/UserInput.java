package br.com.senior.prompthub.domain.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInput {
    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;
}
