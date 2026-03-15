package br.com.senior.prompthub.domain.dto.prompt.output;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagOutput {
    private Long id;
    private String name;
    private String slug;
}
