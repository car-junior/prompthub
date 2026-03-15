package br.com.senior.prompthub.domain.dto.promptversion.output;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOutput {
    private Long id;
    private String username;
}